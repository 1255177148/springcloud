package com.hezhan.client.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hezhan
 * @Date 2019/11/22 11:30
 */
@Configuration
public class MyWebmvcConfiguration implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //配置序列化属性
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue, //是否输出值为null的字段，默认为false
                SerializerFeature.WriteNullStringAsEmpty, //String类型字段值如果为null，则输出为""，而非null
                SerializerFeature.WriteNullListAsEmpty, //List字段值如果为null，则输出[]，而非null
                SerializerFeature.WriteDateUseDateFormat, //全局修改日期格式
                SerializerFeature.DisableCircularReferenceDetect); //消除对同一对象循环引用的问题
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        converters.set(0, fastJsonHttpMessageConverter);
    }
}
