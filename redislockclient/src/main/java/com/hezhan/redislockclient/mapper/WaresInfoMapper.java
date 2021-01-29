package com.hezhan.redislockclient.mapper;

import com.hezhan.redislockclient.entity.po.WaresInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author hezhan
 * @since 2021-01-27
 */
public interface WaresInfoMapper extends BaseMapper<WaresInfo> {

    /**
     * 修改库存量
     * @param id
     * @param inventory
     * @return
     */
    int updateInventory(@Param("id") Integer id, @Param("inventory") Integer inventory);
}
