package com.scaffold.heracles.service;

import com.scaffold.heracles.domain.ConfigExcelModel;

import java.util.List;

/**
 * Created by Karl on 2022/3/4
 **/
public interface HeraclesConfExtractService {
    /**
     * 根据APPID获取所有配置项,并转换成excelmodel,方便上游使用
     *
     * @param appID  应用ID
     * @param isTest 是否测试环境
     * @return 配置列表
     */
    List<ConfigExcelModel> getAllPropertiesByAppID(String appID, boolean isTest);

    /**
     * 1. 相同配置项,相同value,在不同的env被多次生成,env可以合并配置
     * 2. 相同配置项,相同value,在不同的env&widc被多次生成,idc可以合并
     * @param appId
     * AppId
     * @param isTest
     * 是否测试环境
     * @return
     * 可压缩的配置
     */
    List<ConfigExcelModel> listAllCompressableProperties(String appId,boolean isTest);
}
