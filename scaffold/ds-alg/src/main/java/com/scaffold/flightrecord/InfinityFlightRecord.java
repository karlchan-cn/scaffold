package com.scaffold.flightrecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2021/6/16
 **/
public class InfinityFlightRecord {
    public static void main(String[] args) throws InterruptedException {
        boolean flag = true;
        List<Long> datas = new ArrayList<>();
        while (flag) {

            for (int i = 0; i <= 10000000; i++) {
                datas.add((long) i);
            }
            Thread.sleep(10000);
            datas.clear();
        }
    }
}
