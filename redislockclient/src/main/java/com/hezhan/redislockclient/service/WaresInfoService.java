package com.hezhan.redislockclient.service;

import com.hezhan.redislockclient.entity.po.WaresInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hezhan.redislockclient.entity.vo.Demo;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author hezhan
 * @since 2021-01-27
 */
public interface WaresInfoService extends IService<WaresInfo> {



    /**
     * 模拟用户下单时，减去库存，
     * 这里由于只有一条数据，所以商品id和购买量，都预先固定好
     * @return
     */
    boolean updateInventory();
}
