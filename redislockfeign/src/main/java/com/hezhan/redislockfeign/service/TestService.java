package com.hezhan.redislockfeign.service;

import com.hezhan.redislockfeign.service.impl.TestServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date 2021/1/27 18:06
 * @Author hezhan
 */
@FeignClient(value = "REDIS-LOCK-CLIENT", fallback = TestServiceImpl.class)
public interface TestService {

    @PostMapping(value = "/waresInfo/inventory", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean reduceInventory();
}
