package com.hezhan.gateway.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器模型
 * @Date 2021/2/24 14:02
 * @Author hezhan
 */
@Data
public class GatewayFilterDefinition {

    /**
     * 过滤器名称
     */
    private String name;

    /**
     * 对应的路由规则
     */
    private Map<String, String> args;
}
