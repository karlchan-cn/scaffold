package com.scaffold.completablefuture;

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
        ;
        System.out.println("longCompletableFuture.complete:" + longCompletableFuture.complete(1L));
        System.out.println("longCompletableFuture.get():" + longCompletableFuture.get());
    }

    private static CompletableFuture<Long> getLongCompletableFuture() {
        // create a one second timeout future.
        CompletableFuture<Long> longCompletableFuture = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            System.out.println("completedTimeoutWithDefaultValue sleep for 2 seconds begin");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("completedTimeoutWithDefaultValue sleep for 2 seconds ended:" + (System.currentTimeMillis() - start));
            return 99999L;
        });
        return longCompletableFuture;
    }

    private static final void whenCompleteException() {
        CompletableFuture<Long> longCompletableFuture = getLongCompletableFuture().orTimeout(1000, TimeUnit.MILLISECONDS);
        ;
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
        CompletableFuture<Long> longCompletableFuture = getLongCompletableFuture().orTimeout(5000, TimeUnit.MILLISECONDS);
        longCompletableFuture.thenAccept(value -> System.out.println("thenAccept:" + value)).get();

    }

    public static void main(String[] args) throws Exception {
        //returnACompletedFuture();
        // completedTimeoutWithDefaultValue();
        //  whenCompleteException();
        thenAccept();
    }
}
