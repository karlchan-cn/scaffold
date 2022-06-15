package com.scaffold.highperformance;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karl on 2021/12/27
 **/
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@State(Scope.Benchmark)
public class RemoveIfPerformance {
    private static final int NUMBER_OF_LIST_INDEXES = 1_000_000;
    private static final String[] words = "Testing Lamba expressions with this String array".split(" ");

    private ArrayList<String> arrayList;
    private LinkedList<String> linkedList;

    @Setup(Level.Iteration)
    public void setUp() {
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_LIST_INDEXES; i++) {
            arrayList.add(words[i % 6]);
            linkedList.add(words[i % 6]);
        }
    }

    @Benchmark
    public void removeIfArrayList() {
        arrayList.removeIf(x -> x.contains("s"));
    }

    @Benchmark
    public void removeIfLinkedList() {
        linkedList.removeIf(x -> x.contains("s"));
    }

    @Benchmark
    public void removeIteratorArrayList() {
        for (ListIterator<String> it = arrayList.listIterator(arrayList.size()); it.hasPrevious(); ) {
            if (it.previous().contains("s")) {
                it.remove();
            }

        }
    }

    @Benchmark
    public void removeIteratorLinkedList() {
        for (ListIterator<String> it = linkedList.listIterator(linkedList.size()); it.hasPrevious(); ) {
            if (it.previous().contains("s")) {
                it.remove();
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        final RemoveIfPerformance removeIfPerformance = new RemoveIfPerformance();
//        removeIfPerformance.setUp();
//        final long start = System.currentTimeMillis();
//        removeIfPerformance
//                .removeIteratorArrayList();
//        System.out.println("cost = " + (System.currentTimeMillis() - start));
        Main.main(args);
    }
}
