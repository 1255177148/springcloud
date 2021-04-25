package com.hezhan.feign.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hezhan.feign.exception.RemoteAPIException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestTemplateUtil {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 有header参数的get请求
     * @param url 请求路径
     * @param headerParam header参数
     * @param uriVariables 可变参数
     * @return
     */
    public <T> T getForHeader(String url, Map<String, String> headerParam, Map<String, Object> uriVariables, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!CollectionUtils.isEmpty(headerParam)) {
            for (Map.Entry<String, String> entry : headerParam.entrySet()) {
                headers.add(entry.getKey(), getValueEncode(entry.getValue()));
            }
        }
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(headers);
        try {
            if (CollectionUtils.isEmpty(uriVariables)){
                return restTemplate.exchange(url, HttpMethod.GET, httpEntity, clazz).getBody();
            } else {
                return restTemplate.exchange(url, HttpMethod.GET, httpEntity, clazz, uriVariables).getBody();
            }
        } catch (Exception e) {
            throw new RuntimeException("访问" + url + "接口时报错，错误原因为：", e);
        }
    }

    /**
     * 不传header参数的get请求
     * @param url 请求路径
     * @param clazz 指定返回类型
     * @param uriVariables 可变参数
     * @param <T> 返回类型
     * @return
     */
    public <T> T get(String url, Class<T> clazz, Map<String, Object> uriVariables) {
        try {
            if (CollectionUtils.isEmpty(uriVariables)){
                return restTemplate.getForObject(url, clazz);
            } else {
                return restTemplate.getForObject(url, clazz, uriVariables);
            }
        } catch (Exception e) {
            throw new RuntimeException("访问" + url + "接口时报错，错误原因为：", e);
        }
    }

    /**
     * put请求
     * @param url 请求路径
     * @param headerParam header参数
     * @param body body参数
     * @param type 指定返回类型
     * @param <T> 返回类型
     * @return 返回put请求的response
     */
    public <T> T put(String url, Map<String, String> headerParam, Object body, Class<T> type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!CollectionUtils.isEmpty(headerParam)) {
            for (Map.Entry<String, String> entry : headerParam.entrySet()) {
                headers.add(entry.getKey(), getValueEncode(entry.getValue()));
            }
        }
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(JSON.toJSONString(body), headers);
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, type).getBody();
        } catch (Exception e) {
            throw new RuntimeException("访问" + url + "接口时报错，错误原因为：", e);
        }
    }

    /**
     * post请求
     * @param url 请求路径
     * @param headerParam header参数
     * @param body body参数
     * @param type 指定返回类型
     * @param <T> 返回类型
     * @return 返回post请求的response
     */
    public <T> T post(String url, Map<String, String> headerParam, Object body, Class<T> type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!CollectionUtils.isEmpty(headerParam)) {
            for (Map.Entry<String, String> entry : headerParam.entrySet()) {
                headers.add(entry.getKey(), getValueEncode(entry.getValue()));
            }
        }
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(JSON.toJSONString(body), headers);
        try {
            return restTemplate.postForObject(url, httpEntity, type);
        } catch (Exception e) {
            throw new RuntimeException("访问" + url + "接口时报错，错误原因为：", e);
        }
    }

    /**
     * delete请求
     * @param url 请求路径
     * @param headerParam header参数
     */
    public void delete(String url, Map<String, String> headerParam) {
        HttpHeaders headers = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headerParam)) {
            for (Map.Entry<String, String> entry : headerParam.entrySet()) {
                headers.add(entry.getKey(), getValueEncode(entry.getValue()));
            }
        }
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<>(headers);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
        } catch (Exception e) {
            throw new RuntimeException("访问" + url + "接口时报错，错误原因为：", e);
        }
    }

    /**
     * 带返回值的，并且带body参数的delete请求
     * @param url 请求路径
     * @param headerParam header参数
     * @param body body参数
     * @param type 指定返回类型
     * @param <T> 返回类型
     * @return 返回delete请求的response
     */
    public <T> T delete(String url, Map<String, String> headerParam, Object body, Class<T> type){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!CollectionUtils.isEmpty(headerParam)) {
            for (Map.Entry<String, String> entry : headerParam.entrySet()) {
                headers.add(entry.getKey(), getValueEncode(entry.getValue()));
            }
        }
        org.springframework.http.HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(body), headers);
        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, type).getBody();
        } catch (Exception e) {
            throw new RuntimeException("访问" + url + "接口时报错，错误原因为：", e);
        }
    }

    /**
     * 由于OkHttp会对header参数进行校验，不支持中文，所以封装了一个转义方法
     * @param value 校验的header参数
     * @return
     */
    private String getValueEncode(String value){
        if (value == null){
            return "";
        }
        String newValue = value.replace("\n", "");
        for (int i = 0,length = newValue.length();i < length;i++){
            char c = newValue.charAt(i);
            if (c <= 31 && c != '\t' || c >= 127){
                try {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("URLEncoder encode header参数" + newValue + "失败");
                }
            }
        }
        return newValue;
    }
}
