package com.notomatoesplease.logic.impl.fluent;

import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;

public class PizzaSauceTable {

    private final Pizza pizza;

    protected PizzaSauceTable(final Pizza pizza) {
        this.pizza = pizza;
    }

    public PizzaToppingsTable spread(final Sauce sauce) {
        pizza.setSauce(sauce);
        return new PizzaToppingsTable(pizza);
    }
}
