package com.hezhan.feign.service.impl;

import com.hezhan.feign.entity.Hi;
import com.hezhan.feign.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @Author hezhan
 * @Date 2019/9/2 17:26
 */
@Service
public class HelloServiceHystrixImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return null;
    }

    @Override
    public String postHi(Hi name) {
//        throw new RemoteAPIException("client服务宕机");
        return "client服务宕机";
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
