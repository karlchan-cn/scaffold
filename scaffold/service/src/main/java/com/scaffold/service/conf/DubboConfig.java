package com.scaffold.service.conf;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.registry.multicast.MulticastRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDubbo
@DubboComponentScan
public class DubboConfig {
    @Value("${dubbo.inner.registry:zookeeper://127.0.0.1:2181?timeout=60000}")
    private String zkInnerAddr;

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig createApplicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("scaffold-service");
        return applicationConfig;
    }

    @Bean(value = "dubboProtocolConfig")
    @ConditionalOnMissingBean
    public ProtocolConfig createProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        //
        protocolConfig.setThreads(10);
        //
        protocolConfig.setAccepts(1024);
        protocolConfig.setThreadname("GreetingServicePool");
        // netty worker线程数
        protocolConfig.setIothreads(128);
        protocolConfig.setPort(25001);
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
