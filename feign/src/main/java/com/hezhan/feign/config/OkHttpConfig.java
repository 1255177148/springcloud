package com.hezhan.feign.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author elvis
 * @Date 2021/4/12 15:50
 */
@Configuration
public class OkHttpConfig {

    /**
     * 自定义OkHttp3 client配置
     * @return OkHttp3 client
     */
    @Bean
    public OkHttpClient okHttpClient(){
//        Dispatcher dispatcher = new Dispatcher();
//        dispatcher.setMaxRequests(100);// 设置最大请求数
//        dispatcher.setMaxRequestsPerHost(100);// 设置单个ip最大请求数
        return new OkHttpClient().newBuilder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)// 设置连接超时时间
                .readTimeout(10, TimeUnit.SECONDS)// 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)// 设置写超时时间
//                .dispatcher(dispatcher)// 配置分发数
                .connectionPool(pool())// 配置连接池
                .build();
    }

    /**
     * 连接池配置-配置最大空闲连接数、长连接超时时间
     * @return 连接池
     */
    @Bean
    public ConnectionPool pool(){
        return new ConnectionPool(20, 5, TimeUnit.MINUTES);
    }
}
