package com.hezhan.redislockclient.controller;


import com.hezhan.redislockclient.entity.vo.Demo;
import com.hezhan.redislockclient.service.WaresInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品信息表 前端控制器
 * </p>
 *
 * @author hezhan
 * @since 2021-01-27
 */
@RestController
@Slf4j
@RequestMapping("/waresInfo")
public class WaresInfoController {

    @Resource
    private WaresInfoService waresInfoService;

    @PostMapping("/test")
    public String test(@RequestBody Demo demo){
        log.info("进入test()");
        return waresInfoService.hi(demo);
    }

    /**
     * 模拟用户下单，减库存
     * @return
     */
    @PostMapping("/inventory")
    public boolean reduceInventory(){
        return waresInfoService.updateInventory();
    }
}
