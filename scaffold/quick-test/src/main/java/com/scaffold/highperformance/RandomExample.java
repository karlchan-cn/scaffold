package com.scaffold.highperformance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 链接：https://juejin.cn/post/6844904096785235982
 * JDK：11
 * Windows 10 I5-4460/16G
 */
@BenchmarkMode(Mode.Throughput) // 测试类型：吞吐量
//@Threads(16)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class RandomExample {
    public static void main(String[] args) throws RunnerException {
        // 启动基准测试
        Options opt = new OptionsBuilder()
                .include(RandomExample.class.getSimpleName()) // 要导入的测试类
                .warmupIterations(5) // 预热 5 轮
                .measurementIterations(10) // 度量10轮
                .forks(1)
                .build();
        new Runner(opt).run(); // 执行测试
    }

    /**
     * Random 性能测试
     */
    @Benchmark
    public void randomTest() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            // 生成 0-9 的随机数
            random.nextInt(10);
        }
    }
    /**
     * ThreadLocalRandom 性能测试
     */
    @Benchmark
    public void threadLocalRandomTest() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            threadLocalRandom.nextInt(10);
        }
    }
}