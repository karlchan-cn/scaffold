package com.scaffold.log;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;
// entry point for all assertThat methods and utility methods (e.g. entry)
import static org.assertj.core.api.Assertions.*;


public class Slf4jTest {
    @Test
    public void testDemo() {
        //Logger logger = LogManager.getLogger(Slf4jTest.class);
        //logger.info("hello world log4j2");
        org.slf4j.Logger slfLog = LoggerFactory.getLogger(Slf4jTest.class);
        slfLog.info("hello world");

    }
}
