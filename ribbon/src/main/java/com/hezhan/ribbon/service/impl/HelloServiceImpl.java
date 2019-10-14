package com.hezhan.ribbon.service.impl;

import com.alibaba.fastjson.JSON;
import com.hezhan.ribbon.entity.Hi;
import com.hezhan.ribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author hezhan
 * @Date 2019/8/26 11:12
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String sayHi(String name) {
        return restTemplate.getForObject("http://CLIENT/hi?name="+name, String.class);
    }

    @Override
    public String postHi(Hi name) {
//        MultiValueMap<String, String> bodyParam = new LinkedMultiValueMap<>();
//        bodyParam.add("name", name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(name), headers);
        return restTemplate.postForObject("http://client/hi", httpEntity, String.class);
    }

    @Override
    public String putHi(String name) {
        return null;
    }

    @Override
    public String deleteHi(String name) {
        return null;
    }
}
