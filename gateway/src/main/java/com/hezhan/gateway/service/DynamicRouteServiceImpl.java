package com.hezhan.gateway.service;

import com.hezhan.gateway.entity.GatewayRouteDefinition;
import com.hezhan.gateway.util.AssembleUtil;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URISyntaxException;

/**
 * @Date 2021/2/24 14:16
 * @Author hezhan
 */
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Resource
    private AssembleUtil assembleUtil;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 增加路由
     * @param gatewayRouteDefinition 要新增的路由模型
     * @return
     */
    public String add(GatewayRouteDefinition gatewayRouteDefinition) throws URISyntaxException {
        RouteDefinition routeDefinition = assembleUtil.assembleRouteDefinition(gatewayRouteDefinition);
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /**
     * 更新路由
     * @param gatewayRouteDefinition 要更新的路由模型
     * @return
     */
    public String update(GatewayRouteDefinition gatewayRouteDefinition) throws URISyntaxException {
        RouteDefinition routeDefinition = assembleUtil.assembleRouteDefinition(gatewayRouteDefinition);
        try {
            delete(routeDefinition.getId());
        } catch (Exception e){
            return "更新失败，没有找到路由id为：" + routeDefinition.getId() + "的路由";
        }
        try {
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e){
            return "更新路由失败";
        }
    }

    /**
     * 根据路由id删除路由
     * @param id 路由id
     * @return
     */
    public Mono<ResponseEntity<Object>> delete(String id){
        return this.routeDefinitionWriter.delete(Mono.just(id)).then(Mono.defer(() -> {
            return Mono.just(ResponseEntity.ok().build());
        })).onErrorResume((t) -> {
            return t instanceof NotFoundException;
        }, (t) -> {
            return Mono.just(ResponseEntity.notFound().build());
        });
    }
}
