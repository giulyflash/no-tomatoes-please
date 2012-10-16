package com.notomatoesplease.domain;

import com.notomatoesplease.domain.enumeration.IngredientType;

public abstract class Ingredient {

    private String name;
    private int price;
    private boolean visible;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Ingredient [name=");
        builder.append(name);
        builder.append(", price=");
        builder.append(price);
        builder.append(", visible=");
        builder.append(visible);
        builder.append("]");
        return builder.toString();
    }

    public abstract IngredientType getType();

}
