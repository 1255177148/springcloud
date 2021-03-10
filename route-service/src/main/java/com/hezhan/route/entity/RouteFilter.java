package com.hezhan.route.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 路由_过滤器关联表
 * </p>
 *
 * @author hezhan
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouteFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路由id
     */
    @TableField("route_id")
    private String routeId;

    /**
     * 过滤器id
     */
    @TableField("filter_id")
    private String filterId;


}
