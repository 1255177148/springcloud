package com.hezhan.redislockclient.service.impl;

import com.alibaba.fastjson.JSON;
import com.hezhan.redislockclient.entity.vo.Demo;
import com.hezhan.redislockclient.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @Date 2021/3/8 16:52
 * @Author hezhan
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String hi(Demo demo) {
        return JSON.toJSONString(demo);
    }
}
