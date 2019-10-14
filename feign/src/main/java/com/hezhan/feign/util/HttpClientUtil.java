package com.hezhan.feign.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hezhan.feign.exception.RemoteAPIException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpClientUtil {


    @Autowired
    CloseableHttpClient httpClient;

    @Autowired
    RequestConfig requestConfig;

    public static String ERROR_MESSAGE = "ERROR_MESSAGE";

    public static String RESPONSE_RESULT = "RESPONSE_RESULT";

    public static String KEY_HEADER = "KEY_HEADER";

    public static String KEY_BODY = "KEY_BODY";

    public final static String CHARSET_NAME="UTF-8";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Map<String, String> doGet(String url, Map<String, String> headerParamMap) throws IOException {
        logger.debug("Begin do doGet------>url=" + url);
        Map<String, String> result = new HashMap<>();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        if (headerParamMap != null) {
            for (Map.Entry<String, String> entry : headerParamMap.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取结果实体
        result = processResponse(response);
        response.close();
        return result;
    }

    public Map<String, String> doPost(String url, Map<String, Map<String, String>> paramMap) throws IOException {
        logger.debug("Begin do doPost------>url=" + url);
        Map<String, String> result = new HashMap<>();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Map<String, String> bMap = paramMap.get(KEY_BODY);

        if (bMap != null) {
            for (Map.Entry<String, String> entry : bMap.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, CHARSET_NAME));

        logger.debug("请求地址：" + url);
        Map<String, String> hMap = paramMap.get(KEY_HEADER);
        for (Map.Entry<String, String> entity : hMap.entrySet()) {
            httpPost.setHeader(entity.getKey(), entity.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取结果实体
        result = processResponse(response);
        response.close();
        return result;
    }

    public Map<String, String> doPostInfos(String url, Map<String, Map<String, Object>> paramMap) throws IOException {
        logger.debug("Begin do doPostInfos------>url=" + url);
        Map<String, String> result = new HashMap<>();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        Map<String, Object> bMap = paramMap.get(KEY_BODY);
        httpPost.setEntity(new StringEntity(JSON.toJSONString(bMap), ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取结果实体
        result = processResponse(response);
        response.close();
        return result;
    }

    public Map<String, String> doPostInfoForLanguage(String url, Map<String, Object> paramMap) throws IOException {
        logger.debug("Begin do doPostInfoForLanguage------>url=" + url);
        Map<String, String> result = new HashMap<>();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "application/json");
        List bMap = (List) (paramMap.get(KEY_BODY));
        httpPost.setEntity(new StringEntity(JSON.toJSONString(bMap), CHARSET_NAME));

        logger.debug("请求地址：" + url);
        Map<String, Object> hMap = (Map<String, Object>) paramMap.get(KEY_HEADER);
        if (hMap != null) {
            for (Map.Entry<String, Object> entity : hMap.entrySet()) {
                httpPost.setHeader(entity.getKey(), String.valueOf(entity.getValue()));
            }
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取结果实体
        result = processResponse(response);
        response.close();
        return result;
    }

    public Map<String, String> doPut(String url, Map<String, Map<String, String>> paramMap) throws IOException {
        logger.debug("Begin do doPut------>url=" + url);
        Map<String, String> result = new HashMap<>();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Map<String, String> bMap = paramMap.get(KEY_BODY);

        if (bMap != null) {
            for (Map.Entry<String, String> entry : bMap.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        httpPut.setEntity(new UrlEncodedFormEntity(nvps, CHARSET_NAME));

        logger.debug("请求地址：" + url);
        Map<String, String> hMap = paramMap.get(KEY_HEADER);
        for (Map.Entry<String, String> entity : hMap.entrySet()) {
            httpPut.setHeader(entity.getKey(), entity.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(httpPut);
        //获取结果实体
        result = processResponse(response);
        response.close();
        return result;
    }

    private Map<String, String> processResponse(CloseableHttpResponse response) throws IOException {
        Map<String, String> result = new HashMap<>();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                String body = EntityUtils.toString(entity, CHARSET_NAME);
                result.put(ERROR_MESSAGE, null);
                result.put(RESPONSE_RESULT, body);
            }
            EntityUtils.consume(entity);
            //释放链接
        } else {
            result.put(ERROR_MESSAGE, String.valueOf(statusCode));
            result.put(RESPONSE_RESULT, response.getStatusLine().getReasonPhrase());
        }
        return result;
    }

    public static String parseUrl(String url, Map<String, Object> paramsMap) {
        Map<String, String> replaceFields = getUrlReplaceField(url);
        Map<String, String> replaceInfo = new HashMap<>();
        for (Map.Entry<String, String> entry : replaceFields.entrySet()) {
            Object paramValue = paramsMap.get(entry.getKey());
            String value = "";
            if (paramValue != null) {
                value = String.valueOf(paramsMap.get(entry.getKey()));
            }
            replaceInfo.put(entry.getValue(), value);
        }
        for (Map.Entry<String, String> entry : replaceInfo.entrySet()) {
            try {
                url = StringUtils.replace(url, entry.getKey(), URLEncoder.encode(entry.getValue(), CHARSET_NAME));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url;
    }


    private static Map<String, String> getUrlReplaceField(Map<String, String> replaceFields, String url) {
        int startIndex = url.indexOf("{");
        int endIndex = url.indexOf("}") + 1;
        if (startIndex > -1 || endIndex > -1) {
            String field = url.substring(startIndex, endIndex);
            String key = field.substring(1, field.length() - 1);
            replaceFields.put(key, field);
            if (endIndex + 1 <= url.length()) {
                getUrlReplaceField(replaceFields, url.substring(endIndex + 1));
            }
        }
        return replaceFields;
    }

    private static Map<String, String> getUrlReplaceField(String url) {
        Map<String, String> replaceFields = new HashMap<>();
        replaceFields = getUrlReplaceField(replaceFields, url);
        return replaceFields;

    }

    public static JSONArray parseResponseToArray(Map<String, String> response) throws RemoteAPIException {
        JSONArray jsonArray = null;
        if (StringUtils.isEmpty(response.get(ERROR_MESSAGE))) {
            jsonArray = JSONArray.parseArray(response.get(RESPONSE_RESULT));
        } else {
            throw new RemoteAPIException("反序列化调用接口的返回值为JSONArray时报错，状态码为：" + response.get(HttpClientUtil.ERROR_MESSAGE) + " 调用接口返回的信息为： " + response.get(HttpClientUtil.RESPONSE_RESULT));
        }
        return jsonArray;
    }

    public static <T> List<T> parserResponseToList(Map<String, String> response, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (StringUtils.isEmpty(JSONObject.parseObject(response.get(ERROR_MESSAGE), String.class))) {
            if (StringUtils.isNotEmpty(JSONObject.parseObject(response.get(RESPONSE_RESULT), String.class))) {
                list = JSONArray.parseArray(JSONObject.parseObject(response.get(RESPONSE_RESULT), String.class), clazz);
            }
        }
        return list;
    }

    public static JSONObject parseResponse(Map<String, String> response) {
        JSONObject jsonObject = null;
        if (StringUtils.isEmpty(JSONObject.parseObject(response.get(ERROR_MESSAGE), String.class))) {
            jsonObject = JSON.parseObject(JSONObject.parseObject(response.get(RESPONSE_RESULT), String.class));
        } else {
            throw new RemoteAPIException("Exception occur when parseResponse with Response as " + response.get(HttpClientUtil.ERROR_MESSAGE) + " : " + response.get(HttpClientUtil.RESPONSE_RESULT));
        }
        return jsonObject;
    }

}
