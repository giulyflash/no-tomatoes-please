package com.notomatoesplease.domain;

import java.util.List;

public class Pizza {

    private Size size;
    private Dough dough;
    private Sauce sauce;
    private List<Topping> toppings;

    public Pizza(final Size size, final Dough dough, final Sauce sauce, final List<Topping> toppings) {
        this.size = size;
        this.dough = dough;
        this.sauce = sauce;
        this.toppings = toppings;
    }

    public Size getSize() {
        return size;
    }

    public Dough getDough() {
        return dough;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Pizza [size=");
        builder.append(size);
        builder.append(", dough=");
        builder.append(dough);
        builder.append(", sauce=");
        builder.append(sauce);
        builder.append(", toppings=");
        builder.append(toppings);
        builder.append("]");
        return builder.toString();
    }

}
