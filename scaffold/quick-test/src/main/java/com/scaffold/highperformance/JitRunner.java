package com.scaffold.highperformance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * vmargs:
 * -server
 * -XX:PrintIdealGraphLevel=2
 * -XX:PrintIdealGraphFile=ideal.xml
 * -XX:+PrintFlagsFinal
 * -XX:+UnlockDiagnosticVMOptions
 * -XX:+PrintAssembly
 * -XX:+PrintCompilation
 * -XX:+PrintInlining
 * -XX:+PrintCodeCache
 * -XX:+PrintCodeCacheOnCompilation
 * -XX:+LogCompilation
 * -XX:LogFile=JitRunner_Jit.log
 * -Xlog:class+load=debug
 * Created by Karl on 2022/1/11
 **/
public class JitRunner {
    private static int listSize = 0;

    private static class Sum {

        private BigInteger sum;
        private int n;

        Sum(int n) {
            this.n = n;
        }

        synchronized final BigInteger getSum() {
            if (sum == null) {
                sum = BigInteger.ZERO;
                for (int i = 0; i < n; i++) {
                    sum = sum.add(BigInteger.valueOf(i));
                }
            }
            return sum;
        }

    }

    private static class ListSizer {
        public int nlp(int length) {
            int sum = 0;
            for (int i = 0; i < length; i++) {
                sum += i;
            }
            return sum;
        }

        public int getlistSize() {
            return nlp(200);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<BigInteger> list = new ArrayList<>();
        for (int i = 1; i < 1_000_000; i++) {
            Sum sum = new Sum(i % 10_000);
            list.add(sum.getSum());
        }
        System.out.println(list.get(list.size() - 1));
        final ListSizer listSizer = new ListSizer();
        long sum = 0L;
        for (int i = 1; i < 1_000_000; i++) {
            sum += listSizer.getlistSize();
        }
        System.out.println(sum);
        Thread.yield();
//        System.out.println("begin to sleep!" + new JitRunner().getlistSize());
//        Thread.sleep(10_000L);

    }
}
