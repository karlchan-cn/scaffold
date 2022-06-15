package com.scaffold.heracles.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.scaffold.heracles.constants.CloundApplicationConstants;
import com.scaffold.heracles.constants.HeraclesAPIConstants;
import com.scaffold.heracles.constants.HeraclesExtractorConstants;
import com.scaffold.heracles.domain.ConfigExcelModel;
import com.scaffold.heracles.domain.HeraclesConfigInfo;
import com.scaffold.heracles.domain.HeraclesConfigReq;
import com.scaffold.heracles.domain.HeraclesResultDto;
import com.scaffold.heracles.enums.CloudIDC;
import com.scaffold.heracles.service.HeraclesApiService;
import com.scaffold.heracles.service.HeraclesConfExtractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by Karl on 2022/3/7
 **/
@Component
@Slf4j
public class HeraclesConfExtractServiceImpl implements HeraclesConfExtractService {

    private Joiner JOINER = Joiner.on(",").skipNulls();
    private static final CloudIDC[] IDC_DOMAIN = new CloudIDC[]{
            CloudIDC.HT2, CloudIDC.CP, CloudIDC.LY
    };
    private static final CloudIDC[] TEST_DOMAIN = new CloudIDC[]{
            CloudIDC.TEST_CLOUD
    };


    @Override
    public List<ConfigExcelModel> getAllPropertiesByAppID(String appID, boolean isTest) {
        List<ConfigExcelModel> result = new ArrayList<>(200);
        CloudIDC[] domainList;
        String secrect;
        if (isTest) {
            domainList = TEST_DOMAIN;
            secrect = CloundApplicationConstants.getTestSecrect(appID);
        } else {
            domainList = IDC_DOMAIN;
            secrect = CloundApplicationConstants.getIDCSecrect(appID);
        }
        if (domainList.length == 0 || StringUtils.isEmpty(secrect)) {
            log.error("lack of domainlist or secrect.");
            return null;
        }
        for (CloudIDC domain : domainList) {
            //遍历机房列表,进行查询
            HeraclesConfigReq listRelativeConfigReq = new HeraclesConfigReq();
            listRelativeConfigReq.setApiDomain(domain.getApiDomain());
            listRelativeConfigReq.setSecrect(secrect);
            listRelativeConfigReq.setAppId(appID);
            listRelativeConfigReq.setTime(String.valueOf(System.currentTimeMillis()));
            final Map<String, Map<String, List<String>>> envMap = heraclesApiService.queryEnvAndVersionByAppId(listRelativeConfigReq);
            if (CollectionUtils.isEmpty(envMap)) {
                continue;
            }
            for (Map.Entry<String, Map<String, List<String>>> entry : envMap.entrySet()) {
                for (Map.Entry<String, List<String>> detailEnty : entry.getValue().entrySet()) {
                    if (CollectionUtils.isEmpty(detailEnty.getValue())) {
                        continue;
                    }
                    for (String proVersion : detailEnty.getValue()) {
                        listRelativeConfigReq.setEnv(detailEnty.getKey());
                        listRelativeConfigReq.setVersion(proVersion);
                        listRelativeConfigReq.setApiDomain(domain.getApiDomain());
                        listRelativeConfigReq.setSecrect(secrect);
                        final HeraclesResultDto<List<HeraclesConfigInfo>> listHeraclesResultDto = heraclesApiService.listAllRelativeConfig(listRelativeConfigReq);
                        if (listHeraclesResultDto == null || !HeraclesAPIConstants.API_SUCCESS_STATUS.equals(listHeraclesResultDto.getStatus()) ||
                                CollectionUtils.isEmpty(listHeraclesResultDto.getData())) {
                            continue;
                        }
                        final List<HeraclesConfigInfo> datas = listHeraclesResultDto.getData();
                        CountDownLatch cdl = new CountDownLatch(datas.size());
                        for (HeraclesConfigInfo data : datas) {
                            if (!HeraclesAPIConstants.HERACLE_CONFIG_TYPE_PROPERTIES.equals(data.getFormat())) {
                                cdl.countDown();
                                continue;

                            }
                            addPropertyToResult(cdl, result, data, domain, secrect, appID, detailEnty.getKey(), proVersion);
                        }
                        try {
                            cdl.await();
                        } catch (InterruptedException e) {
                            log.error("cdl.await() got error.", e);
                        }
                    }

                }
            }
        }
        return result;
    }

    @Override
    public List<ConfigExcelModel> listAllCompressableProperties(String appId, boolean isTest) {
        final List<ConfigExcelModel> allProperties = getAllPropertiesByAppID(appId, isTest);
        // key = property+value+env,value = version-list
        Map<String, ConfigExcelModel> versionListMap = new HashMap<>();
        // 找出version-list > 0,业务可共用
        for (ConfigExcelModel property : allProperties) {
            versionListMap.computeIfAbsent(property.getPropertyKey() + HeraclesExtractorConstants.EQUALS_STR
                            + property.getPropertyValue(),
                    (String key) -> {
                        final ConfigExcelModel model = new ConfigExcelModel();
                        model.setPropertyKey(property.getPropertyKey());
                        model.setPropertyValue(property.getPropertyValue());
                        model.setVersions(new HashSet<>());
                        return model;
                    }
            ).getVersions().add(property.getPropertyVersion());
        }
        // key = property+value,value = env-list
        Map<String, List<String>> envListMap = new HashMap<>();
        // 找出env-list > 0,业务可共用
        final List<ConfigExcelModel> filteredVersionListMap =
                versionListMap.entrySet().stream().filter(entry -> entry.getValue().getVersions().size() > 1)
                        .map((value) -> {
                            final ConfigExcelModel tmpVal = value.getValue();
                            tmpVal.setEnv(JOINER.join(tmpVal.getVersions()));
                            return tmpVal;
                        }).collect(Collectors.toList());
        return filteredVersionListMap;
    }

    /**
     * 查询单个property文件内容并返回信息.
     *
     * @param cdl        多线程countdown
     * @param result     配置excel model
     * @param configData 配置数据
     * @param idc        idc信息
     * @param secrect    配置秘钥
     * @param appId      appId
     * @param env        环境
     * @param proVersion 配置文件被绑定的版本
     */
    private void addPropertyToResult(CountDownLatch cdl, final List<ConfigExcelModel> result,
                                     HeraclesConfigInfo configData, CloudIDC idc, String secrect, String appId, String env, String proVersion) {
        ES.execute(() -> {
            try {
                HeraclesConfigReq listRelativeConfigReq = new HeraclesConfigReq();
                listRelativeConfigReq.setApiDomain(idc.getApiDomain());
                listRelativeConfigReq.setSecrect(secrect);
                listRelativeConfigReq.setAppId(appId);
                listRelativeConfigReq.setKey(configData.getName());
                listRelativeConfigReq.setVersion(proVersion);
                listRelativeConfigReq.setEnv(env);
                listRelativeConfigReq.setTime(String.valueOf(System.currentTimeMillis()));
                final HeraclesResultDto<String> stringHeraclesResultDto = heraclesApiService.showConfig(listRelativeConfigReq);
                final String propertyValue = stringHeraclesResultDto.getData();
                final String[] propertiesLine = StringUtils.split(propertyValue, HeraclesExtractorConstants.NEW_LINE_STR);
                if (propertiesLine == null) {
                    return;
                }
                for (String line : propertiesLine) {
                    if (StringUtils.isEmpty(line) || StringUtils.trim(line).startsWith(HeraclesExtractorConstants.SHARP_STR)) {
                        continue;
                    }
                    final int eqIdx = line.indexOf(HeraclesExtractorConstants.EQUALS_STR);
                    if (eqIdx < 0) {
                        continue;
                    }
                    final String proKey = line.substring(0, eqIdx);
                    final String proVal = line.substring(eqIdx + 1);
                    ConfigExcelModel model = new ConfigExcelModel();
                    model.setApplication(appId);
                    model.setIdc(idc.getName());
                    model.setPropertyKey(proKey);
                    model.setPropertyFileName(configData.getName());
                    model.setPropertyVersion(proVersion);
                    model.setPropertySourceVersion(configData.getVersion());
                    model.setEnv(env);
                    int length = Math.min(HeraclesExtractorConstants.PROPERTY_VALUE_LIMIT, proVal.length());
                    model.setPropertyValue(proVal.substring(0, length));
                    result.add(model);
                }
            } catch (Exception e) {
                log.error("addPropertyToResult got error", e);
            } finally {
                cdl.countDown();
            }
        });

    }

    private HeraclesApiService heraclesApiService;

    private static final Executor ES = new ThreadPoolExecutor(16, 16, 10_000,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000));

    public HeraclesConfExtractServiceImpl(@Autowired HeraclesApiService heraclesApiService) {
        this.heraclesApiService = heraclesApiService;
    }
}
