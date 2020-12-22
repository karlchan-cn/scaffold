package com.scaffold.service.test.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@ComponentScan(value = {"com.scaffold.service.test.*"})
@Configuration
@Slf4j
@EnableDubbo(scanBasePackages = {"com.scaffold.service.test.*"})
public class TestApplicationConfig {
    @Value("zookeeper://127.0.0.1:2181")
    private String zkInnerAddr;

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig createApplicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("scaffold-service-consumer");
        return applicationConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig createProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setThreads(128);
        protocolConfig.setCorethreads(16);
        protocolConfig.setAccepts(8);
        protocolConfig.setQueues(10000);
        return protocolConfig;
    }

    @Bean("innerRegistry")
    @ConditionalOnMissingBean
    public RegistryConfig createInnerRegistry() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zkInnerAddr);
        registryConfig.setUseAsConfigCenter(false);
        registryConfig.setUseAsMetadataCenter(false);
        return registryConfig;
    }
}
