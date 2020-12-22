package com.scaffold.service.test.rpc;

import com.scaffold.service.rpc.GreetingService;
import com.scaffold.service.test.conf.TestApplicationConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component(value = "GreetingServiceImplTest")
public class GreetingServiceImplTest {
    @DubboReference(version = "1.0.0", group = "local.irg", registry = {"innerRegistry"}, async = true,
            connections = 1, actives = 512,
            timeout = 2000000, loadbalance = "leastactive", check = false, retries = 0)
    private GreetingService greetingService;

    @Before
    public void setUp() throws Exception {
    }

    /**
     * rpc invoke greeting service
     *
     * @return word from greeting service.
     */
    public String invokeGreetingService() {
        int time = 1;
        Object retulst = null;
        while (time-- > 0) {
            try {
                greetingService.greetingWithOneWord();
                retulst = RpcContext.getContext().getCompletableFuture().get(2000000, TimeUnit.MILLISECONDS);
                System.out.println(retulst);
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retulst.toString();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void greetingWithOneWord() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        context.start();
        final GreetingServiceImplTest greetingServiceImplTest = (GreetingServiceImplTest) context.getBean("GreetingServiceImplTest");
        Assert.assertTrue(greetingServiceImplTest.invokeGreetingService().equals("greeting with hello"));
    }
}