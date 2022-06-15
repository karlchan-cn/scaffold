package com.scaffold.heracles.domain;

import com.scaffold.heracles.annotations.ExcelColumn;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * excel结果值
 * @author karl
 **/
@Data
public class ConfigExcelModel {
    public ConfigExcelModel(){

    }
    /**
     * 所属云平台application ID
     *
     */
    @ExcelColumn(col = 1,value = "appId")
    private String application;
    /**
     * 配置文件名字
     */
    @ExcelColumn(col = 2,value = "文件名")
    private String propertyFileName;

    /**
     * 配置key
     */
    @ExcelColumn(col = 3,value = "参数Key")
    private String propertyKey;
    /**
     * 配置value值
     */
    @ExcelColumn(col = 4,value = "参数值")
    private String propertyValue;
    /**
     * 配置所属IDC
     */
    @ExcelColumn(col = 5,value = "数据中心")
    private String idc;
    /**
     * 配置所属版本呢
     */
    @ExcelColumn(col = 6,value = "参数版本")
    private String propertyVersion;
    /**
     * 数据中心共享:是/否
     */
    @ExcelColumn(col = 7,value = "业务共享")
    private boolean bizShare;

    /**
     * 数据中心共享:是/否
     */
    @ExcelColumn(col = 8,value = "idc共享")
    private boolean idcShare;
    /**
     * 配置类型：redis/mq/mysql/jins/zookeeper/elastic_job_zk
     */
    @ExcelColumn(col = 9,value = "参数类型")
    private String propertyType;
    /**
     * 配置文件类型
     */
    @ExcelColumn(col = 10,value = "配置类型")
    private String propertyFileType;
    /**
     * 配置所属环境
     */
    @ExcelColumn(col = 11,value = "环境")
    private String env;
    /**
     * 参数原始版本
     */
    @ExcelColumn(col = 12,value = "参数原始版本")
    private String propertySourceVersion;

    private Set<String> versions;

}
