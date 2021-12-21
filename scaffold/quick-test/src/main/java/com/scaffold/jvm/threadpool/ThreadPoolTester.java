package com.scaffold.jvm.threadpool;

import java.util.concurrent.*;

/**
 * -Xss128k
 */
public class ThreadPoolTester {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        //System.out.println("thread:" + Thread.currentThread().getId() + " infinitited runing");
                        this.run();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        Thread.sleep(5000);
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                System.out.print("thread:" + Thread.currentThread().getId() + " is running");
            });
        }
    }
}
