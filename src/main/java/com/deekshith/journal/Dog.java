package com.deekshith.journal;

import org.springframework.stereotype.Component;

@Component
public class Dog {
    public String fun() {
        return "I am from Base package " +
                "So, ComponentScan Adds this class to the IOC Container as a Bean";
    }
}
