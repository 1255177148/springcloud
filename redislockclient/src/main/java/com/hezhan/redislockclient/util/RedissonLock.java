package com.hezhan.redislockclient.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 使用Redisson框架，基于redis的分布式锁工具类
 *
 * @Date 2021/1/28 17:59
 * @author hezhan
 */
@Component
@Slf4j
public class RedissonLock {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 使用Redisson框架，基于redis来加分布式锁
     *
     * @param lockName  锁的名称
     * @param waitTime  获取锁时的阻塞等待时间，若超出此时间，还没获取到锁，则返回false
     * @param leaseTime 锁的释放时间，防止死锁
     * @param timeUnit  时间单位
     * @return 是否获取到分布式锁
     * @throws InterruptedException
     */
    public boolean lock(String lockName, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        if (null == redissonClient) {
            log.error("RedissonClient is null");
            return false;
        }

        RLock lock = redissonClient.getLock(lockName);
        /**
         * 使用tryLock()获取锁而不是用lock()获取锁，
         * 原因是lock()，如果锁已被占用，则直接线程阻塞，只到锁被释放；
         * 而tryLock()，设定了等待时间,在这个等待时间没过期前，
         * 也会阻塞线程，去反复获取锁，只到获取到锁或者超过等待时间，就返回false。
         */
        boolean result = lock.tryLock(waitTime, leaseTime, timeUnit);
        if (result) {
            log.info("线程 [{}] 获取锁 [{}] 成功", Thread.currentThread().getName(), lockName);
        }
        return result;
    }

    /**
     * 释放分布式锁
     * @param lockName 锁的名称
     * @return 是否释放分布式锁
     */
    public boolean unlock(String lockName) {
        if (null == redissonClient) {
            log.error("RedissonClient is null");
            return false;
        }
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock();
        log.info("线程 [{}] 释放锁 [{}] 成功", Thread.currentThread().getName(), lockName);
        return true;
    }
}
