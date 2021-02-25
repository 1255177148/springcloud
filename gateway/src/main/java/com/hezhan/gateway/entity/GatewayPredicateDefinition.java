package com.hezhan.gateway.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由断言模型
 * @Date 2021/2/24 14:04
 * @Author hezhan
 */
@Data
public class GatewayPredicateDefinition {

    /**
     * 断言对应的name
     */
    private String name;

    /**
     * 配置的断言规则
     */
    private Map<String, String> args;
}
