package com.scaffold.service.test.rpc;

import com.scaffold.service.rpc.GreetingService;
import com.scaffold.service.test.conf.TestApplicationConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component(value = "GreetingServiceImplTest")
public class GreetingServiceImplTest {
    @DubboReference(version = "1.0.0", group = "local.irg", registry = {"innerRegistry"},
            timeout = 200, loadbalance = "leastactive", check = false, retries = 0)
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
        return greetingService.greetingWithOneWord();
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