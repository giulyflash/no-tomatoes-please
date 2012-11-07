package com.notomatoesplease.domain;

public abstract class PizzaProperty {
    private int price;
    private String name;

    public PizzaProperty() {

    };

    public PizzaProperty(final String name, final int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PizzaProperty [name=");
        builder.append(name);
        builder.append(", price=");
        builder.append(price);
        builder.append("]");
        return builder.toString();
    }
}
