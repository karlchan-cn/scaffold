package com.scaffold.heracles.domain;

import lombok.Data;

/**
 * Created by Karl on 2022/3/3
 **/
@Data
public class HeraclesConfigReq {
    /**
     * appID
     */
    private String appId;
    /**
     * 环境
     */
    private String env;
    /**
     * 版本
     */
    private String version;
    /**
     * sdk版本
     */
    private String sdkVersion;
    /**
     * 配置文件名称
     */
    private String key;
    /**
     * 时间戳
     */
    private String time;
    /**
     * 签名
     */
    private String sign;
    /**
     * 配置秘钥
     */
    private String secrect;
    /**
     * 允许定制domain
     */
    private String apiDomain;
}
