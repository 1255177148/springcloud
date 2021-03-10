package com.hezhan.route.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 路由_断言关联表
 * </p>
 *
 * @author hezhan
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoutePredicate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路由id
     */
    @TableField("route_id")
    private String routeId;

    /**
     * 断言id
     */
    @TableField("predicate")
    private String predicate;


}
