package com.hezhan.feign.service;

import com.hezhan.feign.entity.Hi;
import com.hezhan.feign.service.impl.HelloServiceHystrixImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "client", fallback = HelloServiceHystrixImpl.class)
public interface HelloService {

    @GetMapping("/hi")
    String sayHi(@RequestParam("name") String name);

    @PostMapping(value = "/hi",consumes = MediaType.APPLICATION_JSON_VALUE)
    String postHi(@RequestBody Hi name);

    @PutMapping(value = "/hi",consumes = MediaType.APPLICATION_JSON_VALUE)
    String putHi(@RequestBody String name);

    @DeleteMapping("/hi/{name}")
    String deleteHi(@PathVariable("name") String name);
}
