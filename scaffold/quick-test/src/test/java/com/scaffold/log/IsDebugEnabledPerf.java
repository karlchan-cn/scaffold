package com.scaffold.log;

import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 10, time = 2)
@Threads(1)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class IsDebugEnabledPerf {
    private static final Logger LOG = LoggerFactory.getLogger(IsDebugEnabledPerf.class);
    static {
        MDC.put("t-onlinedebug", "on");
    }
    private static boolean isDebugEnabled = false;

    private boolean TRUE_FLAG =  Boolean.TRUE;

    private static final IsDebugEnabledPerf INSTANCE = new IsDebugEnabledPerf();

    @Benchmark
    public void perf1000FormatSync() {
        for (int i = 0; i < 1_000; i++) {
            LOG.info("hello,{}", 1);
        }
        //assertThat(isDebugEnabled).isEqualTo(true);
    }

    @Benchmark
    public void perfOneThousandDebugEnabled() {
        for (int i = 0; i < 1_000; i++) {
            isDebugEnabled = LOG.isDebugEnabled();
        }
        assertThat(isDebugEnabled).isEqualTo(true);
    }

    @Benchmark
    public void perf1000StaticEqual(){
        for (int i = 0; i < 1_000; i++) {
            isDebugEnabled = INSTANCE.TRUE_FLAG;
        }
        assertThat(isDebugEnabled).isEqualTo(true);
    }
}
