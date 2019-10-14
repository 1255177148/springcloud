package com.hezhan.ribbon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author hezhan
 * @Date 2019/8/26 10:51
 */
@Configuration
public class RibbonConfig {

    @Autowired
    HttpClientConfig httpClientConfig;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(){
        return new HttpComponentsClientHttpRequestFactory(httpClientConfig.httpClient());
    }

}
