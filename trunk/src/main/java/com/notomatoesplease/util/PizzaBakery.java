package com.notomatoesplease.util;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;

public class PizzaBakery {
    private Size size;
    private Dough dough;
    private Sauce sauce;
    private List<Topping> toppings = Lists.newArrayList();

    public PizzaBakery takeDough(final Dough dough) {
        this.dough = dough;
        return this;
    }

    public PizzaBakery rollOut(final Size size) {
        this.size = size;
        return this;
    }

    public PizzaBakery spread(final Sauce sauce) {
        this.sauce = sauce;
        return this;
    }

    public PizzaBakery sprinkle(final List<Topping> toppings) {
        this.toppings.addAll(toppings);
        return this;
    }

    public Pizza bake() {
        Preconditions.checkNotNull(size, "Size must not be null!");
        Preconditions.checkNotNull(dough, "Dough must not be null!");
        Preconditions.checkNotNull(sauce, "Sauce must not be null!");
        return new Pizza(size, dough, sauce, toppings);
    }
}
