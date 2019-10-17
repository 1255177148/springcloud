package com.hezhan.client.controller;

import com.hezhan.client.exception.RemoteException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author hezhan
 * @Date 2019/10/17 9:05
 */
@RestController
@RequestMapping("/openApi/v1")
public class RestTemplateController {

    @GetMapping("/test")
    public String test(){
        throw new RemoteException("报错了呦");
    }
}
