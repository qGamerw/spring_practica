package ru.sber.model;

import org.springframework.stereotype.Component;

@Component
public class Dog implements IAnimal {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Dog{ " + name + " }";
    }

    public void setName(String name) {
        this.name = name;
    }
}