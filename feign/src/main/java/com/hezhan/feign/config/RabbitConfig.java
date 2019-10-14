package com.hezhan.feign.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author hezhan
 * @Date 2019/9/18 15:37
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        return new Queue("hello");
    }

    @Bean
    public Queue topicQueue1(){
        return new Queue("topic1");
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue("topic2");
    }

    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout1");
    }

    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout2");
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topic.exchange");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout.exchange");
    }

    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.message");
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }

    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }
}
