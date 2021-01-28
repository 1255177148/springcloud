package com.hezhan.redislockfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RedislockfeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedislockfeignApplication.class, args);
    }

}
