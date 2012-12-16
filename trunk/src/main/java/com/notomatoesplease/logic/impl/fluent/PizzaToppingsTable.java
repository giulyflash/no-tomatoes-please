package com.notomatoesplease.logic.impl.fluent;

import java.util.List;

import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Topping;

public class PizzaToppingsTable {
    private final Pizza pizza;

    protected PizzaToppingsTable(final Pizza pizza) {
        this.pizza = pizza;
    }

    public PizzaOven sprinkle(final List<Topping> toppings) {
        pizza.setToppings(toppings);
        return new PizzaOven(pizza);
    }
}
