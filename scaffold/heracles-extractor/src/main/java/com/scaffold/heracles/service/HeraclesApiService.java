package com.scaffold.heracles.service;

import com.scaffold.heracles.domain.HeraclesConfigInfo;
import com.scaffold.heracles.domain.HeraclesConfigReq;
import com.scaffold.heracles.domain.HeraclesResultDto;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Karl on 2022/3/3
 **/
public interface HeraclesApiService {
    /**
     * 根据appId查询被绑定的环境以及版本信息
     * @param req
     */
    Map<String, Map<String, List<String>>> queryEnvAndVersionByAppId(HeraclesConfigReq req);
    /**
     * 批量下载zip文件
     *
     * @param req
     */
    void downloadFileZip(HeraclesConfigReq req);

    /**
     * 获取应用的配置项内容
     *
     * @param req
     */
    HeraclesResultDto<String> showConfig(HeraclesConfigReq req);

    /**
     * 列举app相关所有的配置信息,包含环境与版本
     *
     * @param req
     *
     */
    HeraclesResultDto<List<HeraclesConfigInfo>> listAllRelativeConfig(HeraclesConfigReq req);

    /**
     * 生成签名
     *
     * @param treeMap
     * @param secretKey
     * @return
     */
    String generateSign(TreeMap<String, String> treeMap, String secretKey);

    String downloadConf();

}
