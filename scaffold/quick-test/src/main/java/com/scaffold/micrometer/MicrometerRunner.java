package com.scaffold.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Created by Karl on 2021/8/13
 **/
public class MicrometerRunner {
    public static void main(String[] args) {
        testSimpleMeter();
    }

    public static void testSimpleMeter() {
        MeterRegistry registry = new SimpleMeterRegistry();
        CompositeMeterRegistry cr = new CompositeMeterRegistry();
        Counter counter = cr.counter("counter");
        counter.increment();
        SimpleMeterRegistry sr = new SimpleMeterRegistry();
        cr.add(sr);
        counter.increment();
        System.out.println("sr counter:" + sr.get("counter").counter().count());
        System.out.println("cr counter:" + cr.get("counter").counter().count());
    }
}
