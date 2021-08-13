package com.scaffold.service.hystrix;

import org.junit.Test;

/**
 * Created by Karl on 2021/8/11
 **/
public class HystraxCommandTester {

    @Test
    public void testCommand() {
        try {
            new HystrixCommand().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("execute ending");
    }
}
