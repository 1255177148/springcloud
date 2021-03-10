package com.hezhan.gateway.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 对象转换工具
 *
 * @Date 2021/3/10 17:17
 * @Author hezhan
 */
public class BeanConverter {

    /**
     * 将指定类型的源对象集合转换为结果对象集合
     *
     * @param source    源对象集合
     * @param converter 对象转换函数
     * @param <S>       源对象类型
     * @param <R>       结果对象类型
     * @return 结果对象集合
     */
    public static <S, R> List<R> convert(Collection<S> source, Function<S, R> converter) {
        if (CollectionUtils.isEmpty(source)) {
            return null;
        }
        return source.stream().map(converter).collect(Collectors.toList());
    }

    /**
     * 将指定类型的源对象集合转换为结果对象集合
     * @param source 源对象集合
     * @param converter 对象转换函数
     * @param resultObjCreator 结果对象的创建函数
     * @param <S> 源对象类型
     * @param <R> 结果对象类型
     * @return 结果对象集合
     */
    public static <S, R> List<R> convert(Collection<S> source, BiFunction<S, Supplier<R>, R> converter, Supplier<R> resultObjCreator){
        if (CollectionUtils.isEmpty(source)) {
            return null;
        }
        return source.stream().map(s -> converter.apply(s, resultObjCreator)).collect(Collectors.toList());
    }

    /**
     * 将指定类型的源对象集合转换为结果对象集合，使用 BaseConverter.convert 作为转换函数
     *
     * @param source 源对象集合
     * @param resultObjCreator 结果对象的创建函数，不能为 null
     * @param <S> 源对象类型
     * @param <R> 结果对象类型
     * @return 结果对象集合
     */
    public static <S, R> List<R> convert(Collection<S> source, Supplier<R> resultObjCreator) {
        if(CollectionUtils.isEmpty(source))
            return null;

        return source.stream().map(s -> convert(s, resultObjCreator)).collect(Collectors.toList());
    }

    /**
     * <p>将指定类型的源对象的转换为结果对象，使用 resultObjCreator 创建对象，使用 BeanUtils.copyProperties 复制对象属性。</p>
     * <p>此方法适用于源对象和目标对象的属性名称及类型都相同的情况。</p>
     * <p>如果源对象和目标对象的属性名称及类型存在部分相同的情况，可以先使用此方法转换相同属性后再专门设置不同的属性值。</p>
     *
     * @param source 源对象
     * @param resultObjCreator 结果对象的创建函数，不能为 null
     * @param <S> 源对象类型
     * @param <R> 结果对象类型
     * @return 结果对象
     */
    public static <S, R> R convert(S source, Supplier<R> resultObjCreator) {
        if(source == null)
            return null;

        R dto = resultObjCreator.get();
        BeanUtils.copyProperties(source, dto);
        return dto;
    }
}
