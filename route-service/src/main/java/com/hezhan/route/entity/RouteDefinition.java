package com.hezhan.route.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 路由定义表
 * </p>
 *
 * @author hezhan
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RouteDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private String id;

    /**
     * 路由执行顺序
     */
    @TableField("route_order")
    private Integer routeOrder;

    /**
     * 路由id
     */
    @TableField("route_id")
    private String routeId;

    /**
     * 路由转发目标uri
     */
    @TableField("route_uri")
    private String routeUri;

    /**
     * 是否启用
     */
    @TableField("enabled")
    private Integer enabled;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    @TableField("version")
    @Version
    private Integer version;

}
