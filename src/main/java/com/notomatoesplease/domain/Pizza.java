package com.notomatoesplease.domain;

import java.util.List;

public class Pizza {

    private final Size size;
    private final Dough dough;
    private final Sauce sauce;
    private final List<Topping> toppings;

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
