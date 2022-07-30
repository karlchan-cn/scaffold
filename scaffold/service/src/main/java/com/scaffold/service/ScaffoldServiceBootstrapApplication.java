package com.scaffold.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class ScaffoldServiceBootstrapApplication {
    public static void main(String[] args) {
        final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(ScaffoldServiceBootstrapApplication.class);
        springApplicationBuilder
                .web(WebApplicationType.NONE);

        springApplicationBuilder.run(args);
        System.out.println("system started ! ");
    }
}
