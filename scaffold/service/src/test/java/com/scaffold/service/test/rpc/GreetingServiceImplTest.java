package com.scaffold.service.test.rpc;

import com.scaffold.service.rpc.GreetingService;
import com.scaffold.service.test.conf.TestApplicationConfig;
import org.apache.dubbo.common.timer.Timer;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.remoting.exchange.support.DefaultFuture;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.*;

@Component(value = "GreetingServiceImplTest")
public class GreetingServiceImplTest {
    @DubboReference(async = true, version = "1.0.0", group = "local.irg",
            connections = 1, actives = 512, url = "dubbo://10.101.129.150:25001",
            timeout = 500, loadbalance = "leastactive", check = false, retries = 0)
    private GreetingService greetingService;


    @Before
    public void setUp() throws Exception {
    }


    /**
     * rpc invoke greeting service
     *
     * @return word from greeting service.
     */
    public String invokeGreetingService() throws IllegalAccessException, InterruptedException, Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(512);
        Map<Long, DefaultFuture> futures = null;
        Timer timer = null;
        Field[] fields = DefaultFuture.class.getDeclaredFields();
        System.out.println(greetingService.greetingWithOneWord());
        System.out.println(new GreetingServiceCommand(greetingService).queue().get(10000, TimeUnit.MILLISECONDS));
        //Thread.sleep(60000);
        for (Field field : fields) {
            field.setAccessible(true);
            if ("FUTURES".equals(field.getName())) {
                futures = (Map<Long, DefaultFuture>) field.get(DefaultFuture.class);
            } else if ("TIME_OUT_TIMER".equals(field.getName())) {
                timer = (Timer) field.get(DefaultFuture.class);
            }
        }
        int time = 10000;
        String retulst = null;
        while (time-- > 0) {
            try {
                final int index = time;
                executorService.submit(() -> {
                    try {
                        System.out.println(" index:" + index + ". " + new GreetingServiceCommand(greetingService).queue().get(200, TimeUnit.MILLISECONDS)
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(10000);
        System.out.printf("futures 's size:" + futures.size());
        return retulst;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void greetingWithOneWord() throws IllegalAccessException, Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        context.start();
        final GreetingServiceImplTest greetingServiceImplTest = (GreetingServiceImplTest) context.getBean("GreetingServiceImplTest");
        Assert.assertTrue(("greeting with hello").equals(greetingServiceImplTest.invokeGreetingService()));
    }
}