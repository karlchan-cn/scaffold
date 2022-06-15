package com.scaffold.highperformance;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karl on 2022/1/4
 **/
@Warmup(iterations = 5, time = 10)
@Measurement(iterations = 10, time = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class StringPerfRunner {

    private static final List<String> strList = new ArrayList<>(10);

    static {
        for (int i = 0; i < 10; i++) {
            strList.add("strListEle" + i);
        }
    }

    private static String result = "";
    private static String strA = "strAA1";
    private static String strB = "strBA1";
    private static String strC = "strCA1";
    private static String strD = "strAA1";
    static {
        if(strA == strD) {
            System.out.println("strA == strD");
        }
    }

    public static void main(String[] args) {
        String tempA = "strAA1";
        if(tempA == strA) {
            System.out.println("strA == tempA");
        }
        String tempANew = strA;
        if(tempANew == strA) {
            System.out.println("strA == tempANew");
        }
    }
    //@JunitPerfConfig(duration = 20_000, warmUp = 10_000L)
    @Benchmark
    public void concatParamWithStringBuilder() {
        StringBuilder sb = new StringBuilder(strA.length() + strB.length() + strC.length());
        result = sb.append(strA).append(strB).append(strC).toString();
    }

    //@JunitPerfConfig(duration = 20_000, warmUp = 10_000L)
    @Benchmark
    public void concatParamWithPlus() {
        String tempResult = strA + strB + strC;
        result = tempResult;
    }

    //@JunitPerfConfig(duration = 20_000, warmUp = 10_000L)
    @Benchmark
    public void foreachWithStringBuilder() {
        int length = 0;
        for (String s : strList) {
            length = s.length();
        }
        StringBuilder sb = new StringBuilder(length);
        for (String s : strList) {
            sb.append(s);
        }
        result = sb.toString();
    }

    //@JunitPerfConfig(duration = 20_000, warmUp = 10_000L)
    @Benchmark
    public void foreachWithConcat() {
        String tempString = null;
        for (String s : strList) {
            tempString += s;
        }
        result = tempString;
    }

    public void testMain() {
        Integer intVal = 1;
        System.out.println("intVal = " + intVal);
        String result = "Hello" + "String" + "Plus" + intVal;
        String result2 = "Hello" + "String" + "Plus" + 2L;
        System.out.println(result);
        System.out.println(result2);
        System.out.println(StringPerfRunner.result);
    }
}
