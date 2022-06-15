package com.scaffold.hzm.domain;

import lombok.Data;

@Data
public class LoginUserReq {
    private String webUserid;
    private String passWord;
    private String code = "";
    private String appId = "HZMBWEB_HK";
    private String joinType = "WEB";
    private String version;
    private String equipment = "PC";
    private String phpSessId;
}
