package com.hezhan.redislockclient.controller;

import com.hezhan.redislockclient.entity.vo.Demo;
import com.hezhan.redislockclient.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Date 2021/3/8 16:52
 * @Author hezhan
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping
    public String test(@RequestBody Demo demo){
        log.info("进入test()");
        return testService.hi(demo);
    }

    @PutMapping
    public String testPut(@RequestBody Demo demo){
        log.info("进入testPut()");
        return testService.hi(demo);
    }
}
