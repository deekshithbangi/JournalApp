package com.deekshith.example;

import org.springframework.stereotype.Component;

@Component
public class Dog {
    public String fun() {
        return "I am from package: example. " +
                "\n Which is not a base package of the project " +
                "and I Have a component annotation";
    }
}
