package com.scaffold.heracles.manage;

import com.scaffold.heracles.domain.CloudApplication;

import java.util.List;

/**
 * Created by Karl on 2022/3/3
 **/
public interface ConfigRepositoryManage {
    /**
     * 根据AppId获取Application
     *
     * @param appId appId
     * @return Application.
     */
    CloudApplication getCloudApplicationByAppId(String appId);

}
