package com.notomatoesplease.domain;

import com.notomatoesplease.domain.enumeration.IngredientType;

public abstract class Ingredient extends PizzaProperty {

    private boolean visible;

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
        builder.append(getName());
        builder.append(", price=");
        builder.append(getPrice());
        builder.append(", visible=");
        builder.append(visible);
        builder.append("]");
        return builder.toString();
    }

    public abstract IngredientType getType();

}
