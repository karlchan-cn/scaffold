package com.scaffold.heracles.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by Karl on 2022/3/3
 **/
@Data
public class CloudApplication {
    /**
     * appId.
     */
    private String appId;
    /**
     * 配置访问秘钥
     */
    private String secrect;
    /**
     * 因为云平台访问配置需要每个机房一个域名,所以需要配置完备
     */
    private List<String> apiDomains;
}
