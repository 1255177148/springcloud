package com.hezhan.gateway.controller;

import com.hezhan.gateway.entity.GatewayRouteDefinition;
import com.hezhan.gateway.service.DynamicRouteServiceImpl;
import com.hezhan.gateway.util.AssembleUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URISyntaxException;

/**
 * <p>通过接口来动态的新增、修改和删除网关路由</p>
 * <p>不过这种做法只能是网关服务是在单机构建的基础上，如果网关服务是集群部署，就不能这样去做</p>
 * <p>可以考虑另外创建一个路由管理的服务，然后同步到mysql和redis里，
 * 网关服务集群定时从redis或者mysql中拉取最新的路由信息，并更新</p>
 * @Date 2021/2/24 15:26
 * @Author hezhan
 */
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Resource
    private DynamicRouteServiceImpl dynamicRouteService;

    /**
     * 增加路由
     * @param gatewayRouteDefinition
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestBody GatewayRouteDefinition gatewayRouteDefinition){
        String flag = "fail";
        try {
            flag = dynamicRouteService.add(gatewayRouteDefinition);
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新路由
     * @param gatewayRouteDefinition
     * @return
     */
    @PutMapping("/update")
    public String update(@RequestBody GatewayRouteDefinition gatewayRouteDefinition) throws URISyntaxException {
        System.out.println("hello");
        return dynamicRouteService.update(gatewayRouteDefinition);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id){
        try {
            return dynamicRouteService.delete(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
