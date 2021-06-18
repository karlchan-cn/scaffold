package com.scaffold.flightrecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Karl on 2021/6/16
 **/
public class InfinityFlightRecord {
    public static void main(String[] args) throws InterruptedException {
        boolean flag = true;
        //List<Long> datas = new ArrayList<>();
        SimpleInliningTest();
//        while (flag) {
//            createLongAndClear(datas);
//        }

    }

    private static void SimpleInliningTest() {
        int sum = 0;
        // 1_000_000 is F4240 in hex
        for (int i = 0; i < 1_000_000_000; i++) {
            sum = add(sum, 99); // 63 hex
        }

        System.out.println("Sum:" + sum);
    }

    private static int add(int a, int b) {
        return a + b;
    }

    private static void createLongAndClear(List<Long> datas) throws InterruptedException {
        //datas.add((long) new Random().nextInt(100000000));
        datas.clear();
    }


}
