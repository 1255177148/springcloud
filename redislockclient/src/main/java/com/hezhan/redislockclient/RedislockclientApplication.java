package com.hezhan.redislockclient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hezhan.redislockclient.mapper")
public class RedislockclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedislockclientApplication.class, args);
    }

}
