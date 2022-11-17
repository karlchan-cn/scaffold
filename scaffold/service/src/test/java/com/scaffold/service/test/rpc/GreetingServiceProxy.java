package com.scaffold.service.test.rpc;

/**
 * GreetingService的代理实现.
 * Created by Karl on 2022/10/18
 **/
public interface GreetingServiceProxy {
    /**
     * 睡眠500ms后返回.
     *
     * @return
     */
    String greetingSleepWith500MS();
}
