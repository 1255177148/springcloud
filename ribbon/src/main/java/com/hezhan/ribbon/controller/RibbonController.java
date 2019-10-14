package com.hezhan.ribbon.controller;

import com.hezhan.ribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hezhan
 * @Date 2019/8/26 11:10
 */
@RestController
public class RibbonController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hi")
    public String sayHi(@RequestParam("name") String name){
        return helloService.sayHi(name);
    }

    @PostMapping("/hi")
    public String postHi(@RequestBody String name){
        return helloService.postHi(name);
    }
}
