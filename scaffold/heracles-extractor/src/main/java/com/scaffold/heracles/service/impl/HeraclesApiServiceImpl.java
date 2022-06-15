package com.scaffold.heracles.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.scaffold.heracles.constants.HeraclesAPIConstants;
import com.scaffold.heracles.domain.HeraclesConfigInfo;
import com.scaffold.heracles.domain.HeraclesConfigReq;
import com.scaffold.heracles.domain.HeraclesResultDto;
import com.scaffold.heracles.service.HeraclesApiService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karl on 2022/3/3
 **/
@Slf4j
@Component
public class HeraclesApiServiceImpl implements HeraclesApiService {
    private static final Gson GSON = new Gson();
    /**
     * 线程安全，可全局使用
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //反序列化的时候如果多了其他属性,不抛出异常
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //如果是空对象的时候,不抛异常
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //属性为null不转换
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    /**
     * 收敛请求广告
     *
     * @param req
     * @param apiPath
     * @return
     */
    private String doHttpRequest(HeraclesConfigReq req, String apiPath) {
        String apiDomain = req.getApiDomain();
        String secrect = req.getSecrect();
        req.setApiDomain(null);
        req.setSecrect(null);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(100_000, TimeUnit.MILLISECONDS)
                .build();
        final String reqJson = GSON.toJson(req);
        TreeMap<String, String> treeMap = GSON.fromJson(reqJson, TreeMap.class);
        StringBuilder urlBuilder = new StringBuilder(apiDomain.length() +
                apiPath.length() +
                reqJson.length() + 100);
        urlBuilder.append(apiDomain).append(apiPath);
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            urlBuilder.append(entry.getKey())
                    .append("=").append(entry.getValue())
                    .append("&");
        }
        urlBuilder.append("sign").append("=").append(generateSign(treeMap, secrect));
        final String url = urlBuilder.toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        log.info("url is:{}", url);
        try (Response response = client.newCall(request).execute()) {
            String httpResult = response.body().string();
            log.info("resp:{}", httpResult);
            return httpResult;
        } catch (IOException e) {
            log.error("get error", e);
            return null;
        }
    }

    @Override
    public Map<String, Map<String, List<String>>> queryEnvAndVersionByAppId(HeraclesConfigReq req) {
        try {
            final String httpResult = doHttpRequest(req, HeraclesAPIConstants.ERECLES_API_QUERY_CONF_BIND_ZONE_AND_ENV);
            TypeReference<Map<String, Map<String, List<String>>>> type = new TypeReference<Map<String, Map<String, List<String>>>>() {
            };
            return OBJECT_MAPPER.readValue(httpResult, type);
        } catch (JsonProcessingException e) {
            log.error("queryEnvAndVersionByAppId got error:{}", req, e);
            return null;
        }

    }

    @Override
    public void downloadFileZip(HeraclesConfigReq req) {

    }

    @Override
    public HeraclesResultDto<String> showConfig(HeraclesConfigReq req) {
        try {
            final String httpResult = doHttpRequest(req, HeraclesAPIConstants.HERECLES_API_SHOW_CONFIG);
            Type type = new TypeReference<HeraclesResultDto<String>>(){}.getType();
            HeraclesResultDto<String> resultVo = JSONObject.parseObject(httpResult, type);
            return resultVo;
//            TypeReference<HeraclesResultDto<String>> type = new TypeReference<HeraclesResultDto<String>>() {
//            };
//            return OBJECT_MAPPER.readValue(httpResult, type);
        } catch (Exception e) {
            log.error("showConfig:{}", req, e);
            return null;
        }


    }

    @Override
    public HeraclesResultDto<List<HeraclesConfigInfo>> listAllRelativeConfig(HeraclesConfigReq req) {
        try {
            final String httpResult = doHttpRequest(req, HeraclesAPIConstants.ERECLES_API_RELATIVE_CONFIG_LIST_PATH);
            TypeReference<HeraclesResultDto<List<HeraclesConfigInfo>>> type = new TypeReference<HeraclesResultDto<List<HeraclesConfigInfo>>>() {
            };
            return OBJECT_MAPPER.readValue(httpResult, type);
        } catch (JsonProcessingException e) {
            log.error("showConfig:{}", req, e);
            return null;
        }
    }

    @Override
    public String generateSign(TreeMap<String, String> treeMap, String secretKey) {
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> pair : treeMap.entrySet()) {
            basestring.append(pair.getKey()).append("=").append(StringUtils.trim(pair.getValue())).append("&");
        }
        basestring.append("secret=" + StringUtils.trim(secretKey));
        log.info("basestring:{}", basestring);
        System.out.println(basestring);
        // 使用MD5对待签名串求签
        return genMd5(basestring.toString());
    }

    @Override
    public String downloadConf() {
        return null;
    }

    private String genMd5(String value) {
        if (null == value) {
            throw new RuntimeException("genMd5 error value is null!");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes("UTF-8"));
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();

        } catch (Exception e) {
            log.error("getMd5 error", e);
        }
        return null;
    }
}
