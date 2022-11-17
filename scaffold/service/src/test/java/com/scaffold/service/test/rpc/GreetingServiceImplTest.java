package com.scaffold.service.test.rpc;

import com.scaffold.service.dubbo.protocol.AdDubboInvoker;
import com.scaffold.service.rpc.GreetingService;
import com.scaffold.service.test.conf.TestApplicationConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.timer.Timer;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.remoting.exchange.support.DefaultFuture;
import org.apache.dubbo.rpc.FutureContext;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;

@Component(value = "GreetingServiceImplTest")
@Slf4j
public class GreetingServiceImplTest {
    @DubboReference(protocol = "" ,async = true, version = "1.0.0", group = "local.irg", url = "dubbo://127.0.0.1:25001",
            loadbalance = "leastactive", check = false, retries = 0, timeout = 30000)
    private GreetingService greetingService;

    @Autowired
    private GreetingServiceProxy greetingServiceProxy;

    public GreetingService getGreetingService() {
        return this.greetingService;
    }

    @Before
    public void setUp() throws Exception {
    }

    public void invoke500Times() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new LinkedList<>();
        for (long i = 0; i <= 200000; i++) {
            list1.add(i % 2);
            list2.add(i % 2);
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        list1.removeIf(ad -> ad.longValue() > 0);
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        list2.removeIf(ad -> ad.longValue() > 0);
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * rpc invoke greeting service
     *
     * @return word from greeting service.
     */
    public String invokeGreetingService() throws Exception {
        //log.error("error for 1234", new Throwable("throw"));
        log.error("error for %d", 100L);
        Map<Long, DefaultFuture> futures = null;
        greetingService.greetingWithOneWordAsync();
        //greetingService.greetingWithOneWordAsync();
        if (true) {
            return "";
        }
        Timer timer = null;
        Field[] fields = DefaultFuture.class.getDeclaredFields();
        //System.out.println(greetingService.greetingWithOneWord());
        System.out.println(new GreetingServiceCommand(greetingService).queue().get(100000, TimeUnit.MILLISECONDS));
        for (Field field : fields) {
            field.setAccessible(true);
            if ("FUTURES".equals(field.getName())) {
                futures = (Map<Long, DefaultFuture>) field.get(DefaultFuture.class);
            } else if ("TIME_OUT_TIMER".equals(field.getName())) {
                timer = (Timer) field.get(DefaultFuture.class);
            }
        }
        int time = 1;
        String retulst = null;
        while (time-- > 0) {
            try {
                // new GreetingServiceCommand(greetingService).queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.printf("sleep for:" + 1 + "s");
        Thread.sleep(1000);
        System.out.println("futures 's size:" + futures.size());
        System.out.println("execute count:" + GreetingServiceCommand.queryExecuteCount() + " execute success count:" + GreetingServiceCommand.queryExecuteSuccessCount()
                + " fall back count:" + GreetingServiceCommand.queryFallBackCount());
        return retulst;
    }

    @Test
    public void greetingWithOneWordAsync() throws InterruptedException, TimeoutException, ExecutionException {
        GreetingServiceImplTest gst = initGreetingTest();
        CompletableFuture<String> firstCf = gst.greetingService.greetingWithOneWordAsync().whenComplete((resultAction));
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            log.error("greetingWithOneWordAsync sleep error", e);
        }
        CompletableFuture<String> seondCF = gst.greetingService.greetingWithOneWordAsync().whenComplete(resultAction);
        CompletableFuture.allOf(firstCf, seondCF).get(3600_000, TimeUnit.MINUTES);
    }

    private BiConsumer<String, Throwable> resultAction = (String result, Throwable t) -> {
        if (t != null) {
            log.error("invoke error", t);
            return;
        }
        log.info("result is :{}", result);
    };

    private GreetingServiceImplTest initGreetingTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        context.start();
        return (GreetingServiceImplTest) context.getBean("GreetingServiceImplTest");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void greetingTestWithTimeout() throws ExecutionException, InterruptedException, TimeoutException {
        //接口默认超时100ms
        final GreetingServiceImplTest greetingServiceImplTest = initGreetingTest();
        final GreetingService gs = greetingServiceImplTest.getGreetingService();
        invokeTimeoutTest(gs, "first invoke with timeout 300ms, result is:{}", "first invoke with timeout 300ms,, invoke exception.cost ms:{}");
        final RpcContext context = RpcContext.getContext();
        // 动态设置超时时间
        context.setAttachment(AdDubboInvoker.DYNAMIC_TIMEOUT_KEY, "2000");
        invokeTimeoutTest(gs, "after consutomed timeout 2000ms, result is:{}", "after consutomed timeout 1000ms, invoke exception.cost ms:{}");
        context.setAttachment(AdDubboInvoker.DYNAMIC_TIMEOUT_KEY, "2000");
        invokeTimeoutTest(gs, "reinvoke after consutomed timeout 2000ms, result is:{}", "reinvoke after consutomed timeout 1000ms, invoke exception.cost ms:{}");
        // 还原超时时间
        context.removeAttachment(AdDubboInvoker.DYNAMIC_TIMEOUT_KEY);
        invokeTimeoutTest(gs, "remove dynamic-timeout, result is:{}", "remove dynamic-timeout, invoke exception.cost ms:{}");
        greetingServiceImplTest.dynamicTimeout();
    }

    private void dynamicTimeout() {
        greetingServiceProxy.greetingSleepWith500MS();
    }


    private void invokeTimeoutTest(GreetingService gs, String msg, String errMsg) {
        long start = System.currentTimeMillis();
        try {
            gs.greetingSleepWith500MS();
            log.info(msg, FutureContext.getContext().getCompletableFuture().get(10000, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            log.error(errMsg, System.currentTimeMillis() - start, e);
        }
    }

    @Test
    public void greetingWithOneWord() throws Exception {
        for (int i = 0; i < 10; i++) {
            //greetingServiceImplTest.invoke500Times();
            //System.out.println("--------");
        }
        initGreetingTest().invokeGreetingService();
        //Assert.assertTrue(("greeting with hello").equals(greetingServiceImplTest.invokeGreetingService()));
    }
}