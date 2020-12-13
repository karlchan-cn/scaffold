package com.scaffold.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableDubbo
public class ScaffoldBootstrapApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ScaffoldBootstrapApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
