package com.scaffold.highperformance;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Karl on 2021/12/27
 **/

public class ValueLooper {
    static Set<Long> longSet = new HashSet<>();
    static Set<ValueLooper> objSet = new HashSet<>();
    static int elementSize = 10_000_000;
    static ArrayList<Long> list = new ArrayList<>();
    static Long[] longArr = new Long[elementSize];
    static ArrayList<ValueLooper> objList = new ArrayList<>();

    static {
        longSet.add(1000L);
        objSet.add(new ValueLooper());
        // 10 million times
        for (int i = 0; i < 10_000_000L; i++) {
            list.add((long) i);
            final ValueLooper e = new ValueLooper();
            objList.add(e);
            e.longVal = (long) i;
            longArr[i] = (long) i;
        }
    }

    private Long longVal;

    static long costTime = 0L;
    static boolean booVal = false;
    static long hashCode = 0L;

    /**
     * [INFO] [2022-01-04 10:13:59.768] [c.s.h.ValueLooper.loopWithList] - Throughput:  31/s (Required: -1/s) - PASSED
     * [INFO] [2022-01-04 10:13:59.768] [c.s.h.ValueLooper.loopWithList] - Memory cost:  16byte
     * [INFO] [2022-01-04 10:13:59.784] [c.s.h.ValueLooper.loopWithList] - Min latency:  28.6119ms (Required: -1.0ms) - PASSED
     * [INFO] [2022-01-04 10:13:59.785] [c.s.h.ValueLooper.loopWithList] - Max latency:  45.8047ms (Required: -1.0ms) - PASSED
     * [INFO] [2022-01-04 10:13:59.809] [c.s.h.ValueLooper.loopWithList] - Avg latency:  31.33263ms (Required: -1.0ms) - PASSED
     */
    @JunitPerfConfig(duration = 10_000)
    public void loopWithList() {

        long marked = System.currentTimeMillis();
        for (int i = 0; i < elementSize; i++) {
            //boolVal = longSet.contains(list.get(i));
            hashCode = list.get(i);
            //lastLong = list.get(i);
        }
        costTime = (System.currentTimeMillis() - marked);
        //System.out.println("Loop times(with long list):" + costTime + "ms" + ",hashCode is:" + boolVal + " " + hashCode);
    }

    /**
     * [INFO] [2022-01-04 10:13:59.758] [c.s.h.ValueLooper.loopWithArray] - Throughput:  34/s (Required: -1/s) - PASSED
     * [INFO] [2022-01-04 10:13:59.758] [c.s.h.ValueLooper.loopWithArray] - Memory cost:  16byte
     * [INFO] [2022-01-04 10:13:59.759] [c.s.h.ValueLooper.loopWithArray] - Min latency:  25.8111ms (Required: -1.0ms) - PASSED
     * [INFO] [2022-01-04 10:13:59.759] [c.s.h.ValueLooper.loopWithArray] - Max latency:  47.816ms (Required: -1.0ms) - PASSED
     * [INFO] [2022-01-04 10:13:59.760] [c.s.h.ValueLooper.loopWithArray] - Avg latency:  29.145107ms (Required: -1.0ms) - PASSED
     */
    @JunitPerfConfig(duration = 10_000)
    public void loopWithArray() {
        long marked = System.currentTimeMillis();
        for (int i = 0; i < elementSize; i++) {
            //boolVal = longSet.contains(longArr[i]);
            hashCode = longArr[i];
            //longSet.contains(longArr[i]);
        }
        costTime = (System.currentTimeMillis() - marked);
        //System.out.println("Loop times(with long array):" + costTime + "ms" + ",hashCode is:" + boolVal + " " + hashCode);
    }

    @JunitPerfConfig(duration = 10_000)
    public void loopWithObject() {
        long marked = System.currentTimeMillis();
        for (int i = 0; i < elementSize; i++) {
            //boolVal = longSet.contains(objList.get(i).longVal);
            hashCode = objList.get(i).longVal;
            // lastObj = objList.get(i);
        }
        costTime = (System.currentTimeMillis() - marked);
        //System.out.println("Loop times(with obj list):" + costTime + "ms" + ",hashCode is:" + boolVal + " " + hashCode);
    }

}
