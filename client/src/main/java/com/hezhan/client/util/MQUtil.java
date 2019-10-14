package com.hezhan.client.util;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author hezhan
 * @Date 2019/9/18 16:27
 */
@Component
public class MQUtil {

    @RabbitListener(queuesToDeclare = {@Queue("hello")})
    public void getMessage(String message) {
        System.out.println("接收到队列hello的消息：" + message);
    }

    @RabbitListener(queuesToDeclare = {@Queue("topic1")})
    public void receive1(String message){
        System.out.println("接收到队列topic1的消息：" + message);
    }

    @RabbitListener(queuesToDeclare = {@Queue("topic2")})
    public void receive2(String message){
        System.out.println("接收到队列topic2的消息：" + message);
    }

    @RabbitListener(queuesToDeclare = {@Queue("fanout1")})
    public void receiveFanout1(String message){
        System.out.println("接收到队列fanout1的消息：" + message);
    }

    @RabbitListener(queuesToDeclare = {@Queue("fanout2")})
    public void receiveFanout2(String message){
        System.out.println("接收到队列fanout2的消息：" + message);
    }
}
