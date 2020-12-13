package com.scaffold.service.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"com.scaffold.service.*"})
@Configuration
@EnableScheduling
@EnableAsync
@Slf4j
//@AutoConfigureAfter(MetricsConfig.class)
public class ApplicationConfig {
}
