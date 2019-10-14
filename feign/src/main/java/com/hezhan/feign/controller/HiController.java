package com.hezhan.feign.controller;

import com.hezhan.feign.entity.Hi;
import com.hezhan.feign.service.HelloSender;
import com.hezhan.feign.service.HelloService;
import com.hezhan.feign.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hezhan
 * @Date 2019/8/29 10:23
 */
@RestController
public class HiController {

    @Autowired
    HelloService helloService;

    @Autowired
    HiService hiService;

    @Autowired
    HelloSender helloSender;

    @GetMapping("/hi")
    public String sayHi(@RequestParam("name") String name){
        return helloService.sayHi(name);
    }

    @PostMapping("/hi")
    public String postHi(@RequestBody Hi name){
        return helloService.postHi(name);
    }

    @PutMapping("/hi")
    public String putHi(@RequestBody String name){
        return helloService.putHi(name);
    }

    @DeleteMapping("/hi/{name}")
    public String deleteHi(@PathVariable("name") String name){
        return helloService.deleteHi(name);
    }

    @PostMapping("/testHi")
    public String test(@RequestBody Hi name){
        return hiService.get(name);
    }

    @GetMapping("/sendMQ")
    public void sendMessage(@RequestParam("message") String message){
        helloSender.send(message);
    }

    @GetMapping("/sendTopic")
    public void sendTopic(@RequestParam("message") String message){
        helloSender.topicSend(message);
    }

    @GetMapping("/sendFanout")
    public void sendFanout(@RequestParam("message") String message){
        helloSender.fanoutSend(message);
    }
}
