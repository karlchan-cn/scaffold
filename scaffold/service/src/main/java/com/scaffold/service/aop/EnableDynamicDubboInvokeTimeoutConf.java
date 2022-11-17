package com.scaffold.service.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Karl on 2022/10/18
 **/
@Configuration
@ComponentScan(value = {"com.scaffold.service.aop.*"})
@EnableAspectJAutoProxy
@Slf4j
public class EnableDynamicDubboInvokeTimeoutConf implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("EnableDynamicDubboInvokeTimeoutConf init");
    }
}
