package com.hezhan.route.entity.vo;

import lombok.Data;

/**
 * @Date 2021/3/10 16:03
 * @Author hezhan
 */
@Data
public class RouteVO {

    private String routeId;

    private Integer routeOrder;

    private String routeUri;

    private Integer enabled;

    private Integer deleted;

    private Integer version;

    private PredicateVO predicates;

    private FilterVO filters;
}
