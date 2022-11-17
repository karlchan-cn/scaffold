package com.scaffold.completablefuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplitableFutureTest {
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> base = CompletableFuture.supplyAsync(() -> {
            // wait 5000ms for remote invoke
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1";
        });
        base = new CompletableFuture<>();
        CompletableFuture<String> future = base.
                thenApply(s -> {
                    return s + " 2";
                })
                .thenApply(s ->
                        {
                            return s + " 3";
                        }
                );
        base.complete("1");
        System.out.println("base complete:" + future.get());
    }


    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> base = new CompletableFuture<>();
        CompletableFuture<String> future = base.thenApply(s -> s + " 2").thenApply(s -> s + " 3");
        future.complete("1");
        System.out.println("future complete:" + future.get());
    }


}
