package com.scaffold.service.conf;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDubbo
@DubboComponentScan
public class DubboConfig {
    @Value("${dubbo.inner.registry}")
    private String zkInnerAddr;

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig createApplicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("scaffold-service");
        return applicationConfig;
    }

    @Bean(value = "dubboProtocolConfig")
    public ProtocolConfig createProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setThreads(256);
        protocolConfig.setAccepts(128);
        protocolConfig.setIothreads(128);
        return protocolConfig;
    }
    //@Bean(value = "httpProtocolConfig")
    public ProtocolConfig createHttpProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("http");
        protocolConfig.setThreads(512);
        protocolConfig.setPort(25002);
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
