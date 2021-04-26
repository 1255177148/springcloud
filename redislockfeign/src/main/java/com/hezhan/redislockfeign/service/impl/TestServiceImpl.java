package com.hezhan.redislockfeign.service.impl;

import com.hezhan.redislockfeign.entity.Demo;
import com.hezhan.redislockfeign.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @Date 2021/1/27 18:06
 * @Author hezhan
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public boolean reduceInventory() {
        return false;
    }

    @Override
    public String test(Demo demo) {
        return null;
    }
}
