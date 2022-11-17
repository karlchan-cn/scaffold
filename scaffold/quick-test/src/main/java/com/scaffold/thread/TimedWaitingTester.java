package com.scaffold.thread;

//import jdk.internal.misc.Unsafe;

import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karl on 2021/9/13
 **/
public class TimedWaitingTester {
    //private static final Unsafe U = Unsafe.getUnsafe();
    public static void main(String[] args) {
//        final Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MINUTE,10);
//        U.park(true,cal.get);
        final CompletableFuture<Long> longCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.printf("begin to sleep for 10000 second");
                Thread.sleep(100_000_000L);
            } catch (InterruptedException e) {

            }
            return 1000L;
        });
        longCompletableFuture.orTimeout(20_000L, TimeUnit.MILLISECONDS);
        longCompletableFuture.join();
    }
}
