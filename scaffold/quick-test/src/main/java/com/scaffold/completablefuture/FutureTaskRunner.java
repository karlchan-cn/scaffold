package com.scaffold.completablefuture;

import java.util.concurrent.*;

/**
 * Created by Karl on 2021/9/16
 **/
public class FutureTaskRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        final ExecutorService executorService = Executors.newFixedThreadPool(20);
        final Future<Long> submit = executorService.submit(() -> {
            Thread.sleep(1000000000);
            return 1L;
        });
        System.out.printf("output is:%d", submit.get(1000000,TimeUnit.MILLISECONDS));
    }
}
