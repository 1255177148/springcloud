package com.hezhan.feign.service.impl;

import com.hezhan.feign.entity.Hi;
import com.hezhan.feign.service.HiService;
import com.hezhan.feign.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author hezhan
 * @Date 2019/8/29 17:26
 */
@Service
public class HiServiceImpl implements HiService {

    @Autowired
    HttpClientUtil httpClientUtil;

    @Override
    public String get(Hi hi) {
        String url = "http://47.101.50.120:8762/hi";
        Map<String, Object> headerParam = new HashMap<>();
        headerParam.put("name", hi.getName());
        Map<String, Map<String, Object>> param = new HashMap<>();
        param.put("KEY_BODY", headerParam);
        Map<String, String> response = new HashMap<>();
        try {
            response = httpClientUtil.doPostInfos(url, param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.get(HttpClientUtil.RESPONSE_RESULT);
    }
}
