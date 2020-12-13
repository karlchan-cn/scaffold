package com.scaffold.service.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * greeting interface default implementation.
 */
@Slf4j
@DubboService(registry = {"innerRegistry"}, group = "${biz.type}.irg", version = "1.0.0", protocol = "dubbo",
        timeout = 200, retries = 0, actives = 512, executes = 512, delay = -1)
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String greetingWithOneWord() {
        return "greeting with hello";
    }
}
