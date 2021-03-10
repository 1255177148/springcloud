package com.hezhan.route.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 断言定义表
 * </p>
 *
 * @author hezhan
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PredicateDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private String id;

    /**
     * 断言名称
     */
    @TableField("name")
    private String name;

    /**
     * 断言参数，多个参数以逗号隔开
     */
    @TableField("args")
    private String args;


}
