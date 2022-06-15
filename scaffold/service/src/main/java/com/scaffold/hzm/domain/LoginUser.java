package com.scaffold.hzm.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class LoginUser {

    private String status;
    private String memberId;
    private String wxAppid;
    private String totalRest;
    private String pointRest;
    private String regTime;
    private String webUserId;
    @JSONField(serialize = false)
    private transient String jwt;

}
