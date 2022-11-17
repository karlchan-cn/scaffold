package com.scaffold.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Karl on 2022/11/7
 **/
public class ExecutorsTester {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutorsTester.class);
    private static ThreadGroup mGroup;
    private static final AtomicInteger mThreadNum = new AtomicInteger(1);

    static {
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    private static final Executor ES = new ThreadPoolExecutor(5, 5, 60 * 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000, true), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            String name = "mPrefix" + mThreadNum.getAndIncrement();
            Thread ret = new Thread(mGroup, runnable, name, 0);
            ret.setDaemon(false);
            return ret;
        }
    }, (runnable, threadPoolExecutor) -> LOG.error("reject for task {} for Executor {}", runnable, threadPoolExecutor));

    public static void main(String[] args) throws InterruptedException {
        LOG.info("begin add thread.");
        for (int i = 0; i < 5; i++) {
            ES.execute(() -> {
                try {
                    Thread.sleep(60_000);
                    LOG.info("finished {}", Thread.currentThread());
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    LOG.error("{} got error", Thread.currentThread(), e);
                }
            });
        }
        LOG.info("add more 5 runnables");
        ES.execute(() -> {
            try {
                LOG.info("start sub task {}", Thread.currentThread());
                Thread.sleep(10_000);
                LOG.info("finished {}", Thread.currentThread());
            } catch (InterruptedException e) {
                LOG.error("{} got error", Thread.currentThread(), e);
            }
        });
        Thread.currentThread().join(20_000_000);
    }
}
