package com.hezhan.client.controller;

import com.alibaba.fastjson.JSON;
import com.hezhan.client.entity.User;
import com.hezhan.client.exception.RemoteException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

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

    @GetMapping("/testHeader")
    public User testHeader(@RequestHeader("name") String name){
        try {
            name = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RemoteException("URLDecoder decode header参数失败");
        }
        User user = new User();
        user.setName(name);
        user.setAge(3);
        user.setDate(new Date());
        return user;
    }

    @PutMapping("/testPut")
    public void testPut(@RequestHeader("name") String name, @RequestBody User user){
        if (StringUtils.isNotEmpty(name) || "hezhan".equals(name)){
            if (user == null){
                throw new RemoteException("body参数为空");
            } else {
                if (!"hezhan".equals(user.getName())){
                    throw new RemoteException("body参数中name字段取值不正确");
                }
            }
        } else {
            throw new RemoteException("header参数中name字段为空");
        }
    }

    @PostMapping("/testPost")
    public String testPost(@RequestHeader("name") String name, @RequestBody User user){
        if (StringUtils.isNotEmpty(name) || "hezhan".equals(name)){
            if (user == null){
                throw new RemoteException("body参数为空");
            } else {
                if (!"hezhan".equals(user.getName())){
                    throw new RemoteException("body参数中name字段取值不正确");
                } else {
                    return "成功";
                }
            }
        } else {
            throw new RemoteException("header参数中name字段为空");
        }
    }

    @DeleteMapping("/testDelete/{id}")
    public void testDelete(@PathVariable("id") String id, @RequestHeader("name") String name){
        if (StringUtils.isEmpty(name)){
            throw new RemoteException("header参数中name字段为空");
        } else {
            if (!"hezhan".equals(id)){
                throw new RemoteException("路径参数不正确");
            }
        }
    }

    @GetMapping("/test/exception")
    public ResponseEntity<String> testException(){
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        String result = "";
        return new ResponseEntity<String>(result, httpStatus);
    }

    @PostMapping("/test/post")
    public String testPostForBody(@RequestBody User user){
        String name = user.getName();
        String address  = user.getAddress();
        return JSON.toJSONString(user);
    }
}
