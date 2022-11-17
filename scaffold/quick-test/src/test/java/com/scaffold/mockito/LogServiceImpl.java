package com.scaffold.mockito;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * Created by Karl on 2022/8/10
 **/
@Slf4j
public class LogServiceImpl implements LogService {
    /**
     * function field.
     */
    private Function<String, String> function;


    @Override
    public void logInfo() {
        log.info("hello log");
    }

    @Override
    public String applyForFunction(String param) {
        return function.apply(param);
    }

}
