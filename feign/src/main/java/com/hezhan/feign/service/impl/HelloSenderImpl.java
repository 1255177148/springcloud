package com.hezhan.feign.service.impl;

import com.hezhan.feign.service.HelloSender;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author hezhan
 * @Date 2019/9/18 15:42
 */
@Service
public class HelloSenderImpl implements HelloSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String message) {
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause)->{
            if (!ack){
                System.out.println("消息唯一标识" + correlationData);
                System.out.println("失败原因" + cause);
            }
        });
        rabbitTemplate.setReturnCallback(this::callBack);
        rabbitTemplate.convertAndSend("hello", message);
    }

    @Override
    public void topicSend(String message) {
        rabbitTemplate.convertAndSend("topic.exchange", "topic.message", message);
        rabbitTemplate.convertAndSend("topic.exchange", "topic.messages", message);
    }

    @Override
    public void fanoutSend(String message) {
        rabbitTemplate.setConfirmCallback(this::confirmCallback);
        rabbitTemplate.setReturnCallback(this::callBack);
        rabbitTemplate.convertAndSend("fanout.exchange", "", message);
    }

    private void confirmCallback(CorrelationData correlationData, boolean ack, String cause){
        System.out.println("发送状态：" + ack);
        System.out.println("消息唯一标识" + correlationData);
        System.out.println("失败原因" + cause);
    }

    private void callBack(Message mes, int replyCode, String replyText, String exchange, String routingKey){
        System.out.println("消息主体message" + mes);
        System.out.println("消息replyCode" + replyCode);
        System.out.println("消息replyText" + replyText);
        System.out.println("消息使用的交换器" + exchange);
        System.out.println("消息使用的路由键" + routingKey);
    }
}
