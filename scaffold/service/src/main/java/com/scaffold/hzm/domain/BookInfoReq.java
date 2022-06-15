package com.scaffold.hzm.domain;

import lombok.Data;

@Data
public class BookInfoReq {
    public static final String LINECODE_HK_ZH = "HKGZHO";
    public static final String PC_VERSION_2_7_202206_1121 = "2.7.202206.1121";
    private String appId = "HZMBWEB_HK";
    private String bookDate;
    private String equipment;
    private String joinType = "WEB";
    private String lineCode;
    private String version;
}
