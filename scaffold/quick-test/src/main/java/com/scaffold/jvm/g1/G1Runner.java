package com.scaffold.jvm.g1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * G1垃圾回收器测试工具类
 * <li> 测试无线创建实例,gc详细日志情况.256MB,80ms最大停顿期望,
 * VM args: -XX:+UseG1GC -XX:+PrintGCDetails -XX:MaxGCPauseMillis=80 -XX:MaxHeapSize=268435456 -XX:+UnlockExperimentalVMOptions -Xlog:gc*:file=gc-G1Runner.log:time,pid:filecount=5,filesize=100m
 * 其他可选项:-XX:+UnlockCommercialFeatures -XX:+FlightRecorder
 */
public class G1Runner {
    private static final List<G1Runner> INFINITY_LIST = new LinkedList<>();
    private Long magicNumber;

    private G1Runner(Long magicNumber) {
        this.magicNumber = magicNumber;
    }

    public static void main(String[] args) throws InterruptedException {

        try {
            Thread.sleep(10000);
            infinityEntry();
        } catch (Throwable e) {
            Thread.sleep(600000);
            e.printStackTrace();
        }
    }

    private static final void infinityEntry() {
        Random random = new Random();
        long count = 0;
        for (; ; ) {
            INFINITY_LIST.add(new G1Runner(random.nextLong()));

            System.out.println("size is:{}" + INFINITY_LIST.size());
        }
    }
}
