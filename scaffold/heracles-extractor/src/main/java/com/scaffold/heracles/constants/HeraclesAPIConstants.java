package com.scaffold.heracles.constants;

/**
 * Created by Karl on 2022/3/3
 **/
public interface HeraclesAPIConstants {
    /**
     * 成功状态码
     */
    Integer API_SUCCESS_STATUS = 200;
    /**
     * kv类型文件.
     */
    String HERACLE_CONFIG_TYPE_KV = "kv";
    /**
     * properties类型文件.
     */
    String HERACLE_CONFIG_TYPE_PROPERTIES = "properties";
    /**
     * 列举所有的配置项
     */
    String ERECLES_API_RELATIVE_CONFIG_LIST_PATH = "/api/v4/app/relativeConfig/list?";
    /**
     * 下载配置内容api
     */
    String HERECLES_API_SHOW_CONFIG = "/api/v4/app/relativeConfig/showConfig?";

    /**
     * 下载zip配置文件
     */
    String ERECLES_API_DOWNLOAD_FILE_ZIP_PATH = "/api/v2/downloadFileZIP?";
    /**
     * 下载zip配置文件
     */
    String ERECLES_API_DOWNLOAD_PATH = "/api/v2/download?";
    /**
     * 查询app绑定的环境以及对应版本
     */
    String ERECLES_API_QUERY_CONF_BIND_ZONE_AND_ENV = "/api/app/bindZone?";

}
