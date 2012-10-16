package com.notomatoesplease.domain;

import java.util.List;

public class Pizza {
    private Size size;
    private Dough dough;
    private Sauce sauce;
    private List<Topping> toppings;

    public Size getSize() {
        return size;
    }

    public void setSize(final Size size) {
        this.size = size;
    }

    public Dough getDough() {
        return dough;
    }

    public void setDough(final Dough dough) {
        this.dough = dough;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(final Sauce sauce) {
        this.sauce = sauce;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(final List<Topping> toppings) {
        this.toppings = toppings;
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
