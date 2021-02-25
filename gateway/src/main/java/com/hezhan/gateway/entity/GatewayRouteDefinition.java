package com.hezhan.gateway.entity;

import lombok.Data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 路由模型
 * @Date 2021/2/24 13:52
 * @Author hezhan
 */
@Data
public class GatewayRouteDefinition {

    /**
     * 路由id
     */
    private String id;

    /**
     * 路由断言集合配置
     */
    private List<GatewayPredicateDefinition> predicates;

    /**
     * 路由过滤器集合配置
     */
    private List<GatewayFilterDefinition> filters;

    /**
     * 路由规则转发的目标uri
     */
    private String uri;

    /**
     * 路由执行的顺序
     */
    private int order;
}
