package com.test5.example1.config;

import com.test5.example1.component.DenpaComponent;
import com.test5.example1.component.TubunComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@ComponentScan("com.test5.example1.component")
@Configuration
public class TwConfig {

    @Bean
    public TubunComponent tubunComponent(@Lazy DenpaComponent denpaComponent) {
        return new TubunComponent(denpaComponent);
    }
}