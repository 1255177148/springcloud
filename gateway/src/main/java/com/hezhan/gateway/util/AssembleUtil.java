package com.hezhan.gateway.util;

import com.hezhan.gateway.entity.GatewayFilterDefinition;
import com.hezhan.gateway.entity.GatewayPredicateDefinition;
import com.hezhan.gateway.entity.GatewayRouteDefinition;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2021/2/24 15:44
 * @Author hezhan
 */
@Component
public class AssembleUtil {

    /**
     * 将GatewayRouteDefinition类的对象组合为RouteDefinition类的对象
     * @param gatewayRouteDefinition
     * @return
     */
    public RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gatewayRouteDefinition) throws URISyntaxException {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRouteDefinition.getId());
        routeDefinition.setOrder(gatewayRouteDefinition.getOrder());
        routeDefinition.setUri(new URI(gatewayRouteDefinition.getUri()));
        // 设置断言
        List<PredicateDefinition> predicates = new ArrayList<>();
        List<GatewayPredicateDefinition> gatewayPredicateDefinitions = gatewayRouteDefinition.getPredicates();
        for (GatewayPredicateDefinition gatewayPredicateDefinition : gatewayPredicateDefinitions){
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setArgs(gatewayPredicateDefinition.getArgs());
            predicateDefinition.setName(gatewayPredicateDefinition.getName());
            predicates.add(predicateDefinition);
        }
        routeDefinition.setPredicates(predicates);

        // 设置过滤器
        List<FilterDefinition> filters = new ArrayList<>();
        List<GatewayFilterDefinition> gatewayFilterDefinitions = gatewayRouteDefinition.getFilters();
        for (GatewayFilterDefinition gatewayFilterDefinition : gatewayFilterDefinitions){
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setArgs(gatewayFilterDefinition.getArgs());
            filterDefinition.setName(gatewayFilterDefinition.getName());
            filters.add(filterDefinition);
        }
        routeDefinition.setFilters(filters);
        return routeDefinition;
    }
}
