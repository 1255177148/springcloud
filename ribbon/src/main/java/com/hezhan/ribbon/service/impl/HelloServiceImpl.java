package com.hezhan.ribbon.service.impl;

import com.hezhan.ribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public String postHi(String name) {
        MultiValueMap<String, String> bodyParam = new LinkedMultiValueMap<>();
        bodyParam.add("name", name);
        return restTemplate.postForObject("http://CLIENT/hi", bodyParam, String.class);
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
