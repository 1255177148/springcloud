package com.hezhan.feign.service.impl;

import com.hezhan.feign.entity.Hi;
import com.hezhan.feign.service.HiService;
import com.hezhan.feign.util.HttpClientUtil;
import com.hezhan.feign.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private RestTemplateUtil restTemplateUtil;

    private static String url = "http://127.0.0.1:8762/hi/test?name={name}&age={age}";

    @Override
    public String get(Hi hi) {
        String url = "http://localhost:8762/hi";
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

    @Override
    public String testHi() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "123");
        map.put("age", "2");
        return restTemplateUtil.get(url, String.class, map);
    }
}
