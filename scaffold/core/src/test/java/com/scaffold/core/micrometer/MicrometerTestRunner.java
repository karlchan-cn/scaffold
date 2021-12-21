package com.scaffold.core.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * Micrometer runner
 */
public class MicrometerTestRunner {
    @Test
    public void testSimpleMeterRegistry(){

        MeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
        // 获取 getSize 方法的 Function 对象
        ToDoubleFunction<List> getSize = List::size;
        List<Integer> l1 = new LinkedList<>();
        getSize.applyAsDouble(l1);
        Counter sumCounter = Metrics.counter("sumCount","region","test");
        List<String> list = simpleMeterRegistry.gauge("listGauge",
                Collections.emptyList(), new ArrayList<>(), List::size);
        Timer timer = Timer
                .builder("my.timer")
                .description("a description of what this timer does") // optional
                .tags("region", "test") // optional
                .register(simpleMeterRegistry);
        //timer.
    }
}
