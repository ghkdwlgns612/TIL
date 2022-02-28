package org.example;

import org.example.di.DependencyClass1;
import org.example.di.DependencyClass1Impl;
import org.example.di.DependencyClass2;
import org.example.di.DependencyClass2Impl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringConfig {

//    private final ApplicationContext applicationContext;
//
//    public SpringConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    @Bean
//    @Scope(value = "prototype")
//    public DependencyClass1 dependencyClass1() {
//        return new DependencyClass1Impl(applicationContext);
//    }
//
//    @Bean
//    public DependencyClass2 dependencyClass2() {
//        return new DependencyClass2Impl(applicationContext);
//    }
}
