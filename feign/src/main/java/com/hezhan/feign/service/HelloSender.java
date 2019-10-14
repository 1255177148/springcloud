package com.hezhan.feign.service;

public interface HelloSender {
    void send(String message);

    void topicSend(String message);

    void fanoutSend(String message);
}
