package com.scaffold.service.test.rpc;

import com.scaffold.service.aop.EnableDynamicDubboInvokeTimeout;
import com.scaffold.service.rpc.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.FutureContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by Karl on 2022/10/18
 **/
@Component
@Slf4j
public class GreetingServiceProxyImpl implements GreetingServiceProxy {

    @DubboReference(async = true, version = "1.0.0", group = "local.irg", url = "dubbo://127.0.0.1:25001",
            loadbalance = "leastactive", check = false, retries = 0, timeout = 300)
    private GreetingService greetingService;


    @Override
    @EnableDynamicDubboInvokeTimeout
    public String greetingSleepWith500MS() {
        long start = System.currentTimeMillis();
        try {
            greetingService.greetingSleepWith500MS();
            final Object result = FutureContext.getContext().getCompletableFuture()
                    .get(2000, TimeUnit.MILLISECONDS);
            log.info("GreetingServiceProxyImpl.greetingSleepWith500MS:{}", result);
            return result.toString();
        } catch (Exception e) {
            log.error("GreetingServiceProxyImpl.greetingSleepWith500MS error:{}", System.currentTimeMillis() - start, e);
            return "error";
        }
    }
}
