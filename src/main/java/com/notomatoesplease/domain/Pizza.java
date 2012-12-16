package com.notomatoesplease.domain;

import java.util.List;

public class Pizza {

    private Size size;
    private Dough dough;
    private Sauce sauce;
    private List<Topping> toppings;

    public void setSize(final Size size) {
        this.size = size;
    }

    public void setDough(final Dough dough) {
        this.dough = dough;
    }

    public void setSauce(final Sauce sauce) {
        this.sauce = sauce;
    }

    public void setToppings(final List<Topping> toppings) {
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

    public int getTotalPrice() {
        int price = 0;
        price += size.getPrice();
        price += dough.getPrice();
        price += sauce.getPrice();

        for (final Topping top : toppings) {
            price += top.getPrice();
        }

        return price;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Pizza [totalPrice=");
        builder.append(getTotalPrice());
        builder.append(" ,size=");
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
