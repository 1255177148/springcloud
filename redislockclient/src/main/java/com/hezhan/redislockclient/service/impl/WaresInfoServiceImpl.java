package com.hezhan.redislockclient.service.impl;

import com.hezhan.redislockclient.entity.po.WaresInfo;
import com.hezhan.redislockclient.mapper.WaresInfoMapper;
import com.hezhan.redislockclient.service.WaresInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hezhan.redislockclient.util.RedissonLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
@Slf4j
public class WaresInfoServiceImpl extends ServiceImpl<WaresInfoMapper, WaresInfo> implements WaresInfoService {

    @Resource
    private RedissonLock redissonLock;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private WaresInfoMapper waresInfoMapper;

    @Value("${server.port}")
    private String port;

    @Override
    public String hi() {
        return port;
    }

    @Override
    public boolean updateInventory() {
        boolean result = false;
        // 获取分布式锁
        RLock lock = redissonClient.getLock("lock");
        try {
            // 加分布式锁
            lock.lock(2L, TimeUnit.SECONDS);
            result = reduceInventory();

        } catch (Exception e){
            log.error(e.getMessage());
        } finally {
            lock.unlock();
        }
        return result;
    }

    /**
     * @Transactional 此注解如果在类上注解，那么只会作用在此类执行的第一个方法上，
     * 也就是如果此类中 A 方法 调用 B方法，那么 如果调用了A 方法，B方法的这个注解是不会起作用的，只有A方法才会生成事务
     * @return
     */
    @Transactional
    public boolean reduceInventory(){
        WaresInfo waresInfo = getById(1);
        if (waresInfo == null){
            return false;
        }
        int surplus = waresInfo.getInventory() - 1; // 数据库的库存减去用户购买的数量，得到购买后的剩余库存
        return waresInfoMapper.updateInventory(1, surplus) >= 0;
    }
}
