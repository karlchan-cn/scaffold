package com.scaffold.highperformance;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karl on 2022/1/4
 **/
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 10, time = 2)
@Threads(1)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MapPerfRunner {
    private static Map<Integer, Integer> MAP_FIELD;
    private static final int PARAM_SIZE = 16;
    private static final Integer[] LONG_PARAMS = new Integer[PARAM_SIZE];

    static {
        final Random random = new Random();
        for (int j = 0; j < PARAM_SIZE; j++) {
            LONG_PARAMS[j] = random.nextInt(1_000_000);
        }
    }

    @Benchmark
    public void puthWithSize() {
        Map<Integer, Integer> map = null;
        for (int i = 0; i < 1_000_000; i++) {
            map = new HashMap<>(PARAM_SIZE << 1, 1F);
            for (int j = 0; j < PARAM_SIZE; j++) {
                map.put(LONG_PARAMS[j], LONG_PARAMS[j]);
            }
        }
        MAP_FIELD = map;
    }

    @Benchmark
    public void putWithoutSize() {
        Map<Integer, Integer> map = null;
        for (int i = 0; i < 1_000_000; i++) {
            map = new HashMap<>();
            for (int j = 0; j < PARAM_SIZE; j++) {
                map.put(LONG_PARAMS[j], LONG_PARAMS[j]);
            }
        }
        MAP_FIELD = map;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> sizedMap = new HashMap<>(8);
        sizedMap.put(1, 1);
    }


}
