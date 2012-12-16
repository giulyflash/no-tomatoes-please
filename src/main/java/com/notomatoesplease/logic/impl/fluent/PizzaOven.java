package com.notomatoesplease.logic.impl.fluent;

import com.notomatoesplease.domain.Pizza;

public class PizzaOven {

    private final Pizza pizza;

    protected PizzaOven(final Pizza pizza) {
        this.pizza = pizza;
    }

    public Pizza bake() {
        return pizza;
    }
}
