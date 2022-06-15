package com.scaffold.highperformance;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Karl on 2022/1/21
 **/
//@BenchmarkMode(Mode.AverageTime)
//@Warmup(iterations = 1, time = 1)
//@Measurement(iterations = 2, time = 2)
//@Threads(1)
//@Fork(1)
//@State(value = Scope.Benchmark)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ExecutorPerfRunner implements Runnable {
    private static final int EXECUTION_COUNT  = 100_000;
    private static CountDownLatch CDL;
    private static final ExecutorPerfRunner INSTANT = new ExecutorPerfRunner();
    private static final ExecutorService ELASTIC_CORE_ES = new ThreadPoolExecutor(8, 256, 0,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(EXECUTION_COUNT), new ThreadPoolExecutor.AbortPolicy());
    private static final ExecutorService STATIC_CORE_ES = new ThreadPoolExecutor(256, 256, 0,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(EXECUTION_COUNT), new ThreadPoolExecutor.AbortPolicy());
    private static AtomicInteger COUNTER;

    private void initCounter(){
        CDL = new CountDownLatch(EXECUTION_COUNT);
        COUNTER = new AtomicInteger();
    }
    /**
     * min-core
     */
    //@Benchmark
    @JunitPerfConfig(duration = 10_000)
    public void perfJmhForElasticCore() throws InterruptedException {
        initCounter();
        for(int i = 0;i< EXECUTION_COUNT;i++){
            ELASTIC_CORE_ES.submit(INSTANT);
        }
        CDL.await();

    }

    /**
     * 固定core测试
     */
    //@Benchmark
    @JunitPerfConfig(duration = 10_000)
    public void perfJmhForStaticCore() throws InterruptedException {
        initCounter();
        for(int i = 0;i< EXECUTION_COUNT;i++){
            STATIC_CORE_ES.submit(INSTANT);
        }
        CDL.await();
    }

    @Override
    public void run() {
        if (CDL != null) {
            CDL.countDown();
        }
        COUNTER.incrementAndGet();
    }

}
