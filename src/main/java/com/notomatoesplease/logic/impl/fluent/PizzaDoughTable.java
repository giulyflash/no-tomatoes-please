package com.notomatoesplease.logic.impl.fluent;

import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Size;

public class PizzaDoughTable {

    private final Pizza pizza;

    protected PizzaDoughTable(final Pizza pizza) {
        this.pizza = pizza;
    }

    public PizzaSauceTable rollOut(final Size size) {
        pizza.setSize(size);
        return new PizzaSauceTable(pizza);
    }

}
