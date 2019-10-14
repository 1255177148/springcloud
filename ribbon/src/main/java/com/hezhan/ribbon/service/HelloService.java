package com.hezhan.ribbon.service;

import com.hezhan.ribbon.entity.Hi;

public interface HelloService {
    String sayHi(String name);
    String postHi(Hi name);
    String putHi(String name);
    String deleteHi(String name);
}
