package com.scaffold.completablefuture;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

//@Slf4j
public class CompletableFutureTest {
    private static final ExecutorService es = new ThreadPoolExecutor(1, 1, 60L,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>(1000), new ThreadPoolExecutor.DiscardPolicy());
    private static final Logger log = LoggerFactory.getLogger(ComplitableFutureTest.class);

    @Test
    public void testExecuteCompletableFutureRunnable() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            final int task = i;
            log.info("task {} executed to es ",task);
            es.execute(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    log.info("threa {} execute task {} before sleep error", Thread.currentThread().getName(), task, e);
                }
                log.info("threa {} execute task {}", Thread.currentThread().getName(), task);
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(100000L);
                    } catch (InterruptedException e) {
                        log.error("threa {} execute task {} error.", Thread.currentThread().getName(), task, e);
                    }
                    return "";
                });
            });
            //Thread.sleep(1000L);
        }
        Thread.sleep(100000L);
    }
}
