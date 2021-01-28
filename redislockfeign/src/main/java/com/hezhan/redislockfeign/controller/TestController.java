package com.hezhan.redislockfeign.controller;

import com.hezhan.redislockfeign.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2021/1/27 18:06
 * @Author hezhan
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("/test")
    public String test(){
        return testService.demo();
    }

    /**
     * 负载调用减库存，模拟微服务下，集群之间的线程数据安全问题；
     * 我们知道，常见的Thread线程安全中的加锁lock，只是单个客户端服务下的线程安全，本质上是单个服务的jvm在处理，
     * 而目前微服务环境下，是容器化集群部署，会有多个服务，多个服务间jvm的线程没有联系，所以常用的关于线程的加锁，
     * 对于微服务的环境，没有任何用处，数据依旧会存在线程安全问题。
     * 我们这里模拟的就是一个简单的用户下单时，减库存的操作，看多个服务中，减库存的操作是否会引发线程安全问题
     * @return
     */
    @PostMapping("inventory")
    public boolean inventory(){
        return testService.reduceInventory();
    }
}
