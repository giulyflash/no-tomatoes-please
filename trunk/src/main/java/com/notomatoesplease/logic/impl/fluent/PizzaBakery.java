package com.notomatoesplease.logic.impl.fluent;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;

public class PizzaBakery {
    public PizzaDoughTable knead(final Dough dough) {
        final Pizza pizza = new Pizza();
        pizza.setDough(dough);
        return new PizzaDoughTable(pizza);
    }
}
