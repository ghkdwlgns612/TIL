package org.example.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class DependencyClass1Impl implements DependencyClass1{

    private final ApplicationContext applicationContext;
    private final DependencyClass2 dependencyClass2;

    @Autowired
    public DependencyClass1Impl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.dependencyClass2 = applicationContext.getBean(DependencyClass2.class);
    }

    @Override
    public void run() {

    }

    @Override
    public void shot() {

    }
}
