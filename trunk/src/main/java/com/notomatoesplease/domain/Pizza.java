package com.notomatoesplease.domain;

import java.util.List;

public class Pizza {
    private Ingredient dough;
    private Ingredient sauce;
    private List<Ingredient> toppings;

    public Ingredient getDough() {
        return dough;
    }

    public void setDough(final Ingredient dough) {
        this.dough = dough;
    }

    public Ingredient getSauce() {
        return sauce;
    }

    public void setSauce(final Ingredient sauce) {
        this.sauce = sauce;
    }

    public List<Ingredient> getToppings() {
        return toppings;
    }

    public void setToppings(final List<Ingredient> toppings) {
        this.toppings = toppings;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Pizza [dough=");
        builder.append(dough);
        builder.append(", sauce=");
        builder.append(sauce);
        builder.append(", toppings=");
        builder.append(toppings);
        builder.append("]");
        return builder.toString();
    }

}
