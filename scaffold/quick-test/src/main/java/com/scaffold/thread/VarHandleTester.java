package com.scaffold.thread;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

/**
 * Created by Karl on 2021/9/13
 **/
public class VarHandleTester {
    private static final MethodHandles.Lookup l = MethodHandles.lookup();
    volatile long count;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        final VarHandleTester varHandleTester = new VarHandleTester();
        final VarHandle count = l.findVarHandle(VarHandleTester.class, "count", long.class);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> count.getAndAdd(varHandleTester, 1L)).start();
        }
        System.out.println("sleep for 10 seconds");
        Thread.sleep(10000);
        System.out.println("result is = " + count.get(varHandleTester));
    }
}
