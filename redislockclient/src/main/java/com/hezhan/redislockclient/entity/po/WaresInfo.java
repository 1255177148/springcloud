package com.hezhan.redislockclient.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author hezhan
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wares_info")
public class WaresInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 商品编号
     */
    @TableField("code")
    private String code;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 生产厂商
     */
    @TableField("manufacturer")
    private String manufacturer;

    /**
     * 店铺编号
     */
    @TableField("shop_code")
    private String shopCode;

    /**
     * 库存量
     */
    @TableField("inventory")
    private Integer inventory;


}
