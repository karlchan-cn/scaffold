package com.scaffold.heracles.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaffold.heracles.domain.HeraclesConfigReq;
import com.scaffold.heracles.domain.HeraclesResultDto;
import com.scaffold.heracles.enums.CloudIDC;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Karl on 2022/3/3
 **/
public class HeraclesApiServiceImplTest {
    HeraclesApiServiceImpl puller = new HeraclesApiServiceImpl();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Test
    public void queryEnvAndVersionByAppIdForTest() {
        HeraclesConfigReq req = new HeraclesConfigReq();
        req
                .setAppId("ad-show-gateway-service");

        req.setSecrect("e93da51f2a3b9fd92e53f00ae0b3b2e3");
        req.setApiDomain(CloudIDC.TEST_CLOUD.getApiDomain());
        Assert.assertNotNull(puller.queryEnvAndVersionByAppId(req));

    }

    @Test
    public void listAllRelativeConfig() {
        HeraclesConfigReq req = new HeraclesConfigReq();
        req
                .setAppId("ad-show-gateway-service");
        req
                .setEnv("idc_bjht");
        req.setVersion("cpd_1_0_0_0");
        req.setTime(String.valueOf(System.currentTimeMillis()));
        req.setSecrect("beb199d0fc7e62f427bef901ebcbb0ae");
        //Assert.assertNotNull(puller.listAllRelativeConfig(req));
    }
    @Test
    public void jacksonErrorTest() throws JsonProcessingException {
        String jsonStr = "{\"status\":200,\"msg\":\"SUCCESS\",\"data\":\"diagnose_sampling_conf={\\\"grobalRatioIn10E4\\\":0,\\\"imeiWhiteList\\\": [\\\"201707049983254\\\",\\\"ouid123123123\\\"],\\\"specialPosRatioIn10E4Map\\\": {\\\"23213\\\": 10000,\\\"23212\\\": 10000},\\\"specialOwnerRatioIn10E4Map\\\": {\\\"11\\\": 10000,\\\"22\\\": 10000},\\\"specialAdIdRatioIn10E4Map\\\": {\\\"33\\\": 10000,\\\"44\\\": 10000},\\\"competitionSamplingConf\\\":{\\\"queueSamplingTopAdSizeMap\\\":{\\\"1\\\":50,\\\"2\\\":20,\\\"3\\\":200,\\\"4\\\":500},\\\"queueDagradationLevelMap\\\":{\\\"1\\\":0},\\\"followAdFilterDiagnoseHitResult\\\":false,\\\"globalRatioIn10E4\\\":5,\\\"imeiWhiteList\\\":[\\\"201707049983254\\\",\\\"ouid123123123\\\"],\\\"specialPosRatioIn10E4Map\\\":{}}}\\n\\n\\n\",\"success\":true}";
        TypeReference<HeraclesResultDto<String>> type = new TypeReference<HeraclesResultDto<String>>() {
        };
        final HeraclesResultDto<String> result = OBJECT_MAPPER.readValue(jsonStr, type);
    }

    @Test
    public void listAllRelativeConfigForTest() {
        HeraclesConfigReq req = new HeraclesConfigReq();
        req
                .setAppId("ad-show-gateway-service");
        req
                .setEnv("test_cloud");
        req.setVersion("test_cpd_lc_1_0_0");
        req.setTime(String.valueOf(System.currentTimeMillis()));
        req.setSecrect("e93da51f2a3b9fd92e53f00ae0b3b2e3");
        req.setApiDomain(CloudIDC.TEST_CLOUD.getApiDomain());
        Assert.assertNotNull(puller.listAllRelativeConfig(req));
    }

    @Test
    public void showConfigForTest() {
        HeraclesConfigReq req = new HeraclesConfigReq();
        req
                .setAppId("ad-show-gateway-service");
        req
                .setEnv("test_cloud");
        req.setVersion("test_cpd_lc_1_0_0");
        req.setKey("diagnose_dynamic.properties");
        req.setTime(String.valueOf(System.currentTimeMillis()));
        req.setSecrect("e93da51f2a3b9fd92e53f00ae0b3b2e3");
        req.setApiDomain(CloudIDC.TEST_CLOUD.getApiDomain());
        System.out.println(puller.showConfig(req));
    }

    @Test
    public void generateSign() {
    }

    @Test
    public void downloadConf() {
    }
}