package com.scaffold.hzm.service;

import com.scaffold.hzm.domain.*;

import java.util.List;

/**
 * 请求登录服务
 */
public interface TicketService {
    /**
     * 登录服务器
     *
     * @param user 用户
     * @return 成功/失败
     */
    ResultDto<LoginUser> login(LoginUserReq user);

    ResultDto<List<BookInfo>> getBookInfo(BookInfoReq req, LoginUser user);


}
