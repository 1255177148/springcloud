package com.hezhan.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hezhan.gateway.util.ConversionUtil;
import com.hezhan.gateway.util.IpUtil;
import com.hezhan.gateway.util.RedisUtil;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>全局过滤器</p>
 * <p>所有的请求都会过滤</p>
 * <p>可拦截get、post等等类型的请求，然后做逻辑处理</p>
 * @Date 2021/3/4 14:39
 * @Author hezhan
 */
@Component
@Slf4j
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ConversionUtil conversionUtil;

    /**
     * 请求频率限制时间
     */
    private static final long OFFSET = 3L;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String url = serverHttpRequest.getURI().toString();
        System.out.println("uri=" + url);
        HttpMethod method = serverHttpRequest.getMethod();
        URI uri = serverHttpRequest.getURI();
        if (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method)){
            AtomicReference<String> bodyRef = new AtomicReference<>();
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                        DataBufferUtils.retain(dataBuffer);
                        bodyRef.set(charBuffer.toString());
                        String bodyStr = formatStr(bodyRef.get());
                        log.info("url为:{}\n请求类型为:{}\nbody参数为:{}", url, method.name(), bodyStr);
                        // start 将body数据再次封装
                        DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
                        Flux<DataBuffer> cachedFlux = Flux.just(bodyDataBuffer);
                        // 这里要移除掉原来的header中的Content-Length参数，因为拦截后，这个值可能和原来的不一样，可能会引起400错误
                        ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).headers(httpHeaders -> {
                            httpHeaders.putAll(exchange.getRequest().getHeaders());
                            httpHeaders.remove("Content-Length");
                        }).build();
                        //封装request，传给下一级
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(request) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        // end

                        /**
                         * 下面这段逻辑主要是可以限制规定时间内多次携带同样的参数重复请求同一个接口
                         */
//                        // 缓存请求到redis
//                        String clientIp = IpUtil.getClientIp(serverHttpRequest);
//                        log.info("客户端的真实地址：ip ={}", clientIp);
//                        String key = clientIp + "-" + uri.toString();
//                        if (redisUtil.hasKey(key)) {
//                            //判断缓存参数是否相同
//                            if (StringUtils.isEmpty(bodyStr)) {
//                                //参数为空存入key
//                                bodyStr = key;
//                            }
//                            String param = conversionUtil.toString(redisUtil.get(key));
//                            if (bodyStr.equals(param)) {
//                                //参数相同表示重复请求，再次刷新缓存时间
//                                redisUtil.set(key, bodyStr, OFFSET);
//                                //自定义响应结果
//                                ServerHttpResponse response = exchange.getResponse();
//                                JSONObject message = new JSONObject();
//                                message.put("status", -1);
//                                message.put("data", "重复请求");
//                                byte[] bits = JSON.toJSONString(message).getBytes(StandardCharsets.UTF_8);
//                                DataBuffer buffer = response.bufferFactory().wrap(bits);
//                                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//                                //指定编码，否则在浏览器中会中文乱码
//                                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//                                return response.writeWith(Mono.just(buffer));
//                            }
//                        }
//                        //第一次请求放入缓存
//                        redisUtil.set(key, bodyStr, OFFSET);

                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    });
        } else if (HttpMethod.GET.equals(method)){
            MultiValueMap<String, String> requestQueryParams = serverHttpRequest.getQueryParams();
            // 获取到query参数后，可以做想做的业务

            log.info("url为:{}\n请求类型为:{}\n参数为:{}", url, method.name(), JSON.toJSONString(requestQueryParams));
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

    /**
     * 执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }


    private DataBuffer stringBuffer(String value){
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 去除多余空格和换行制表符
     * @param str
     * @return
     */
    private static String formatStr(String str){
        if (str != null && str.length() > 0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
        return str;
    }
}
