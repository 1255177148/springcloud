package com.hezhan.redislockclient.service.impl;

import com.hezhan.redislockclient.entity.po.WaresInfo;
import com.hezhan.redislockclient.mapper.WaresInfoMapper;
import com.hezhan.redislockclient.service.WaresInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hezhan.redislockclient.util.RedissonLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author hezhan
 * @since 2021-01-27
 */
@Service
@Transactional
@Slf4j
public class WaresInfoServiceImpl extends ServiceImpl<WaresInfoMapper, WaresInfo> implements WaresInfoService {

    @Resource
    private RedissonLock redissonLock;

    @Value("${server.port}")
    private String port;

    @Override
    public String hi() {
        return port;
    }

    @Override
    public boolean updateInventory() {
        boolean result = false;
        try {
            // 加分布式锁
            boolean isLock = redissonLock.lock("lock", 100, 2, TimeUnit.SECONDS);
            if (isLock){
                try {
                    result = reduceInventory();
                } finally {
                    redissonLock.unlock("lock");
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    private boolean reduceInventory(){
        WaresInfo waresInfo = getById(1);
        if (waresInfo == null){
            return false;
        }
        int surplus = waresInfo.getInventory() - 1; // 数据库的库存减去用户购买的数量，得到购买后的剩余库存
        waresInfo.setInventory(surplus);
        return updateById(waresInfo);
    }
}
