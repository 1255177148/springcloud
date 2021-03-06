package com.hezhan.singleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SingleserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleserverApplication.class, args);
    }

}
