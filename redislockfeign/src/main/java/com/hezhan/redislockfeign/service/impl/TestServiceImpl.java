package com.hezhan.redislockfeign.service.impl;

import com.hezhan.redislockfeign.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @Date 2021/1/27 18:06
 * @Author hezhan
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String demo() {
        return "";
    }

    @Override
    public boolean reduceInventory() {
        return false;
    }
}
