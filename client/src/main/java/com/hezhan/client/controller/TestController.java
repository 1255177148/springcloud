package com.hezhan.client.controller;

import com.hezhan.client.entity.Hi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hezhan
 * @Date 2019/8/26 11:00
 */
@RestController
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hi")
    public String sayHi(@RequestParam("name")String name){
        String result = "hi:" + name +",this server port is:" + port;
        return result;
    }

    @PostMapping("/hi")
    public String postHi(@RequestBody Hi name){
        String user = name.getName();
        String result = "hi:" + user +",this server port is:" + port;
        return result;
    }

    @PutMapping("/hi")
    public String putHi(@RequestBody String name){
        String result = "hi:" + name +",this server port is:" + port;
        return result;
    }

    @DeleteMapping("/hi/{name}")
    public String deleteHi(@PathVariable("name") String name){
        String result = "hi:" + name +",this server port is:" + port;
        return result;
    }
}
