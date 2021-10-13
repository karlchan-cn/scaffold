package com.scaffold.completablefuture;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by karl on 2021/5/24
 **/
public class CompletableFutureRunner {
    /**
     * return a completed future.
     *
     * @return 1000L result future.
     */
    private static final void returnACompletedFuture() {
        CompletableFuture<Long> longCompletableFuture = CompletableFuture.completedFuture(1000L);
        CompletableFuture.allOf(null);
        try {
            System.out.println("returnACompletedFuture:" + longCompletableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * complete timeout with default value.
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static final void completedTimeoutWithDefaultValue() throws ExecutionException, InterruptedException {
        CompletableFuture<Long> longCompletableFuture = getLongCompletableFuture().orTimeout(1000, TimeUnit.MILLISECONDS);
        System.out.println("longCompletableFuture.complete:" + longCompletableFuture.complete(1L));
        System.out.println("longCompletableFuture.get():" + longCompletableFuture.get());
    }

    private static CompletableFuture<Long> getLongCompletableFuture() {
        // create a one second timeout future.
        CompletableFuture<Long> longCompletableFuture = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            System.out.println("completedTimeoutWithDefaultValue sleep for 2000 seconds begin");
            try {
                Thread.sleep(2000000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("completedTimeoutWithDefaultValue sleep for 2000 seconds ended:" + (System.currentTimeMillis() - start));
            return 99999L;
        });
        return longCompletableFuture;
    }

    private static final void whenCompleteException() {
        CompletableFuture<Long> longCompletableFuture = getLongCompletableFuture().orTimeout(1000000L, TimeUnit.MILLISECONDS);
        longCompletableFuture.whenComplete((v, e) -> {
            System.out.println("exception is:" + e);
            System.out.println("value is:" + v);
        });
        try {
            System.out.println("whenCompleteException.get():" + longCompletableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static final void thenAccept() throws ExecutionException, InterruptedException {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final CompletableFuture<Long> longCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("sleep for 2 seconds");
            System.out.println("current time 1:" + sdf.format(Calendar.getInstance().getTime()));
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1L;
        });
        System.out.println("current timestamp1:" + sdf.format(Calendar.getInstance().getTime()));
        CompletableFuture.allOf(longCompletableFuture1).whenCompleteAsync((value, throwable) -> {
            System.out.println("current time 2:" + sdf.format(Calendar.getInstance().getTime()));
        });
        System.out.println("current timestamp2:" + sdf.format(Calendar.getInstance().getTime()) );
        CompletableFuture<Long> longCompletableFuture = getLongCompletableFuture().orTimeout(5000000L, TimeUnit.MILLISECONDS);
        longCompletableFuture.thenAccept(value -> System.out.println("thenAccept:" + value)).get();

    }

    public static void main(String[] args) throws Exception {
        //returnACompletedFuture();
        // completedTimeoutWithDefaultValue();
        //  whenCompleteException();
        thenAccept();
    }
}
