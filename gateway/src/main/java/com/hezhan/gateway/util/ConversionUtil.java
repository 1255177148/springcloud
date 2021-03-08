package com.hezhan.gateway.util;

import org.springframework.stereotype.Component;

/**
 * 类型转换工具类
 * @Date 2021/3/8 14:31
 * @Author hezhan
 */
@Component
public class ConversionUtil {

    /**
     * Object类型转换为String类型
     * @param o 要转换的值
     * @return
     */
    public String toString(Object o){
        return o == null ? null : o.toString();
    }
}
