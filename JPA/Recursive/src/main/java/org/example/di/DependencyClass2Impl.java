package org.example.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class DependencyClass2Impl implements DependencyClass2{

    private final ApplicationContext applicationContext;
    private final DependencyClass1 dependencyClass1;

    @Autowired
    public DependencyClass2Impl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.dependencyClass1 = applicationContext.getBean(DependencyClass1.class);
    }

    @Override
    public void quit() {

    }

    @Override
    public void see() {

    }
}
