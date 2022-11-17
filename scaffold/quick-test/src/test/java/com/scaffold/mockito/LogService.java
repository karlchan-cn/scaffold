package com.scaffold.mockito;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * Created by Karl on 2022/8/10
 **/

public interface LogService {

    /**
     * log info.
     */
    void logInfo();

    /**
     * apply function.
     *
     * @param param
     * @return apply result.
     */
    String applyForFunction(String param);

}
