package com.scaffold.heracles.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 云平台APP常量.
 * Created by Karl on 2022/3/3
 **/
public final class CloundApplicationConstants {
    public static final String GATEWAY_APP_ID = "ad-show-gateway-service";
    public static final String GATEWAY_SECRECT = "";
    public static final String MIXER_APP_ID = "ad-show-mixer-service";
    public static final String PERFORMANCE_DSP_APP_ID = "ad-show-performance-service";
    public static final String BRAND_DSP_APP_ID = "ad-show-brand-retrieve-service";
    public static final String RECALL_SVC_APP_ID = "ad-show-recall-svc";
    public static final String RECALL_INDEX_INDEX_APP_ID = "ad-show-recall-index";
    public static final String AD_DETAIL_SVC_APP_ID = "ad-details-service";
    private static final Map<String, String> IDC_SECRECT_MAP = new HashMap<>();
    private static final Map<String, String> TEST_SECRECT_MAP = new HashMap<>();

    /**
     * 获取测试环境配置秘钥
     *
     * @param appId appId
     * @return 配置秘钥
     */
    public static String getTestSecrect(String appId) {
        return TEST_SECRECT_MAP.get(appId);
    }

    /**
     * 获取线上app对应的秘钥
     *
     * @param appId appId
     * @return 秘钥
     */
    public static String getIDCSecrect(String appId) {
        return IDC_SECRECT_MAP.get(appId);
    }

    static {
        IDC_SECRECT_MAP.put(GATEWAY_APP_ID, "beb199d0fc7e62f427bef901ebcbb0ae");
        TEST_SECRECT_MAP.put(GATEWAY_APP_ID, "e93da51f2a3b9fd92e53f00ae0b3b2e3");

        IDC_SECRECT_MAP.put(MIXER_APP_ID,"b2b42d28abf75aa3dc49f08b5e6629cf");
        TEST_SECRECT_MAP.put(MIXER_APP_ID,"d4b9d10f1b5891058e8d695e29273a38");

        IDC_SECRECT_MAP.put(PERFORMANCE_DSP_APP_ID,"dbf9700c42d9a98912f84e059e51c346");
        TEST_SECRECT_MAP.put(PERFORMANCE_DSP_APP_ID,"6a0f352363debf1687c47dbfa6703534");

        IDC_SECRECT_MAP.put(RECALL_SVC_APP_ID,"6c9b72f0921fb2de4bcdd0b86ad37905");
        TEST_SECRECT_MAP.put(RECALL_SVC_APP_ID,"6c9b72f0921fb2de4bcdd0b86ad37905");

        IDC_SECRECT_MAP.put(AD_DETAIL_SVC_APP_ID,"2071dd99f0cd7bbdeb28b5971caac736");
        TEST_SECRECT_MAP.put(AD_DETAIL_SVC_APP_ID,"b81b21458dbd13cd9bfe71df1e39fd04");
    }
}
