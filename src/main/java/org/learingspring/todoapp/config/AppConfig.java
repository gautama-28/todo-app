package org.learingspring.todoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class AppConfig {
    @Bean
    public AtomicInteger idGenerator(){
        return new AtomicInteger(1);
    }
}
