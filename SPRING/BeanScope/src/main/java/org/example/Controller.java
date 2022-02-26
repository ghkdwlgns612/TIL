package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private MyComponent myComponent;
    private MyInterface myInterface;

    @Autowired
    public Controller(MyComponent myComponent, MyInterface myInterface) {
        this.myComponent = myComponent;
        this.myInterface = myInterface;
    }

    @GetMapping
    public void testController() {
        System.out.println("myComponent = " + myComponent);
        System.out.println("myInterface = " + myInterface);
    }
}
