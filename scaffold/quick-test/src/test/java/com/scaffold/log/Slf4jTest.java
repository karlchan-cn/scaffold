package com.scaffold.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.assertj.core.api.Assertions.assertThat;
// entry point for all assertThat methods and utility methods (e.g. entry)

public class Slf4jTest {
    private static final Logger log4j = LogManager.getLogger(Slf4jTest.class);
    private static final org.slf4j.Logger slfLog = LoggerFactory.getLogger(Slf4jTest.class);

    @Test
    public void testDemo() {
        log4j.info("hello world log4j2");
        assertThat(slfLog.isDebugEnabled()).isEqualTo(false);
        MDC.put("t-onlinedebug", "on");
        assertThat(slfLog.isDebugEnabled()).isEqualTo(true);
        slfLog.info("hello world");
    }

    @Test
    public void testFormat() {
        slfLog.info("hello,{}", 1);
    }
}
