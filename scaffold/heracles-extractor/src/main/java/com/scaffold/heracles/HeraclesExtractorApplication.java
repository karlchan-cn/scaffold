package com.scaffold.heracles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * mvn clean package spring-boot:repackage
 * Created by Karl on 2022/3/4
 **/

@SpringBootApplication
public class HeraclesExtractorApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(HeraclesExtractorApplication.class);

        SpringApplication.run(HeraclesExtractorApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
}
