package com.scaffold.hzm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.scaffold.hzm.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Slf4j
public class TicketServiceImpl implements TicketService {
    String INIT_URL = "https://i.hzmbus.com/webhtml/login";
    String INDEX_URL = "https://i.hzmbus.com/webhtml/index.html";
    String LOGIN_URL = "https://i.hzmbus.com/webh5api/login";
    String BOOK_INFO_URL = "https://i.hzmbus.com/webh5api/manage/query.book.info.data";
    String USER_AGEN = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.115 Safari/537.36";
    private String COOKIE_ACW_SC__V2 = "acw_sc__v2";
    private String COOKIE_PHPSESSID = "PHPSESSID";
    private String DEFAULT_PHPSESSID = "hb19ct1it45lk4heh387kxvgtpn";
    private CloseableHttpClient instance;

    private CloseableHttpClient getClient(String phpSess, String authorization) {

        //acw_tc=2f6a1fb216550446382746201e1d7d6ed4be91b272b4c9c1d92dee17cbb599; acw_sc__v2=62a5fa1e04c6d2e93bb0cab02db19bf1d07158c6
        BasicClientCookie acwCookie = new BasicClientCookie(COOKIE_ACW_SC__V2, "62a754c62d31a1f508a2fe249d1bed23cee8fed0");
        acwCookie.setDomain("i.hzmbus.com");
        acwCookie.setPath("/");
        if (StringUtils.isEmpty(phpSess)) {
            phpSess = DEFAULT_PHPSESSID;
        }
        BasicClientCookie phpSessIdCookie = new BasicClientCookie(COOKIE_PHPSESSID, phpSess);
        phpSessIdCookie.setDomain("i.hzmbus.com");
        phpSessIdCookie.setPath("/");
        CookieStore cs = new BasicCookieStore();
        cs.addCookie(phpSessIdCookie);
        cs.addCookie(acwCookie);
        CloseableHttpClient client = HttpClients.custom()
                .setUserAgent(USER_AGEN).setDefaultCookieStore(cs).build();
        return client;
    }

    @Override
    public ResultDto<LoginUser> login(LoginUserReq req) {
        HttpPost post = new HttpPost(LOGIN_URL);
        post.setEntity(new StringEntity(JSON.toJSONString(req), ContentType.APPLICATION_JSON));
        try {
            CloseableHttpResponse response = getClient(req.getPhpSessId(), null).execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            String respStr = EntityUtils.toString(response.getEntity());
            System.out.println("respStr = " + respStr);
            ResultDto<LoginUser> loginResult = JSON.parseObject(respStr, new TypeReference<ResultDto<LoginUser>>() {
            });

            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header header : headers) {
                log.info("head:{},value:{}", header.getName(), header.getValue());
                System.out.println("header = " + header.getName() + ",value=" + header.getValue());
            }
            return loginResult;
        } catch (IOException e) {
            log.error("query error:", e);
        }
        return null;
    }

    @Override
    public ResultDto<List<BookInfo>> getBookInfo(BookInfoReq req, LoginUser user) {
        CloseableHttpClient client = getClient(null, user.getJwt());
        HttpPost post = new HttpPost(BOOK_INFO_URL);
        post.addHeader("Authorization", user.getJwt());
        post.setEntity(new StringEntity(JSON.toJSONString(req), ContentType.APPLICATION_JSON));
        try {
            CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            String respStr = EntityUtils.toString(response.getEntity());
            System.out.println("respStr = " + respStr);
            ResultDto<List<BookInfo>> bookInfoRet = JSON.parseObject(respStr, new TypeReference<ResultDto<List<BookInfo>>>() {
            });

            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header header : headers) {
                log.info("head:{},value:{}", header.getName(), header.getValue());
                System.out.println("header = " + header.getName() + ",value=" + header.getValue());
            }
            return bookInfoRet;
        } catch (IOException e) {
            log.error("query error:", e);
        }
        return null;
    }
}
