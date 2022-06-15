package com.scaffold.heracles.manage.impl;

import com.scaffold.heracles.constants.CloundApplicationConstants;
import com.scaffold.heracles.domain.CloudApplication;
import com.scaffold.heracles.manage.ConfigRepositoryManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2022/3/3
 **/
public class ConfigRepositoryManageImpl implements ConfigRepositoryManage {
    private static List<String> ONLINE_ENVS = new ArrayList<>();
    private static List<String> TEST_ENVS = new ArrayList<>();
    //private static List<>
    /**
     * 临时存储
     */
    private static Map<String, CloudApplication> CACHE = new HashMap<>();

    static {
        CloudApplication gateway = new CloudApplication();
        gateway.setAppId(CloundApplicationConstants.GATEWAY_APP_ID);
        gateway.setSecrect("beb199d0fc7e62f427bef901ebcbb0ae");
        CACHE.put("ad-show-gateway-service", gateway);
    }

    @Override
    public CloudApplication getCloudApplicationByAppId(String appId) {
        return null;
    }
}
