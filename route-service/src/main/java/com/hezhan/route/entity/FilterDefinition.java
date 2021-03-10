package com.hezhan.route.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 过滤器模型定义表
 * </p>
 *
 * @author hezhan
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FilterDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableField("id")
    private String id;

    /**
     * 过滤器名称
     */
    @TableField("name")
    private String name;

    /**
     * 过滤器参数，多个参数以逗号隔开
     */
    @TableField("args")
    private String args;


}
