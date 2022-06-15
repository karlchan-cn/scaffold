package com.scaffold.highperformance;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Karl on 2021/12/27
 **/
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 2, time = 2)
@Threads(1)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ListStreamPerformance {
    private Long id;


    public Long getId() {
        return this.id;
    }

    static List<Long> resultList = null;
    static List<ListStreamPerformance> objList = new ArrayList<>();
    static int counter = 0;

    static {
        for (long i = 0; i < 10; i++) {
            final ListStreamPerformance e = new ListStreamPerformance();
            e.id = i;
            objList.add(e);
        }
    }

    //    public static void main(String[] args) {
//        final Stream<Long> longStream = objList.stream().map(ListStreamPerformance::getId);
//        longStream.collect(Collectors.toList());
//    }
    @JunitPerfConfig(duration = 10_000)
    @Benchmark
    public void readListSize() {
        for (int i = 0; i < objList.size(); i++) {
            counter++;
        }
    }
    @JunitPerfConfig(duration = 10_000)
    @Benchmark
    public void readTempListSize() {

        int iCount = objList.size();
        for (int i = 0; i < iCount; i++) {
            counter++;
        }

    }


    @JunitPerfConfig(duration = 10_000)
    @Benchmark
    public void streamPerformanceTest() {
        //1.801          ms/op
        // 778622/s (Required: -1/s) - PASSED
        resultList = objList.stream().map(ListStreamPerformance::getId).collect(Collectors.toList());
    }

    @JunitPerfConfig(duration = 10_000)
    @Benchmark
    public void foreachPerformanceTest() {
        //1.418          ms/op
        // Throughput:  1014307/s (Required: -1/s) - PASSED
        List<Long> tempResult = new ArrayList<>(objList.size());
        for (int i = 0; i < objList.size(); i++) {
            tempResult.add(objList.get(i).getId());
        }
        resultList = tempResult;
    }
}
