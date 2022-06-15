package com.scaffold.hzm.service;

import com.alibaba.fastjson.JSON;
import com.scaffold.hzm.domain.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketServiceImplTest {
    TicketService service = new TicketServiceImpl();

    @Test
    public void login() {

        getLoginUser();
    }

    private LoginUser getLoginUser() {
        LoginUserReq loginUser = JSON.parseObject("{\"webUserid\":\"277588464@qq.com\",\"passWord\":\"Karl1234\",\"code\":\"\",\"appId\":\"HZMBWEB_HK\",\"joinType\":\"WEB\",\"version\":\"2.7.202204.1115\",\"equipment\":\"PC\"}"
                , LoginUserReq.class);
        ResultDto<LoginUser> userResultDto = service.login(loginUser);
        assertNotNull(userResultDto);
        assertNotNull(userResultDto.getResponseData());
        assertNotNull(ResultDto.CODE_SUCCESS.equals(userResultDto.getCode()));
        userResultDto.getResponseData().setJwt(userResultDto.getJwt());
        return userResultDto.getResponseData();
    }

    @Test
    public void bookInfo() {
        LoginUser loginUser = getLoginUser();
        BookInfoReq bookinfoReq = JSON.parseObject("{\"bookDate\":\"2022-06-19\",\"lineCode\":\"HKGZHO\",\"appId\":\"HZMBWEB_HK\",\"joinType\":\"WEB\",\"version\":\"2.7.202206.1121\",\"equipment\":\"PC\"}"
                , BookInfoReq.class);
        assertNotNull(service.getBookInfo(bookinfoReq, loginUser));
    }
}