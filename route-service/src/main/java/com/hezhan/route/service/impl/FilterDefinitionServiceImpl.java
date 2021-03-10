package com.hezhan.route.service.impl;

import com.hezhan.route.entity.FilterDefinition;
import com.hezhan.route.mapper.FilterDefinitionMapper;
import com.hezhan.route.service.FilterDefinitionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 过滤器模型定义表 服务实现类
 * </p>
 *
 * @author hezhan
 * @since 2021-03-10
 */
@Service
public class FilterDefinitionServiceImpl extends ServiceImpl<FilterDefinitionMapper, FilterDefinition> implements FilterDefinitionService {

}
