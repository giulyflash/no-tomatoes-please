package com.notomatoesplease.userinterface.gui.widget;

import java.util.List;

import javax.swing.JComboBox;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Ingredient;

public final class PizzaIngredientComboBox<T extends Ingredient> extends JComboBox<String> {

    private static final long serialVersionUID = -870096608460863910L;
    private final List<T> ingredients = Lists.newArrayList();

    /**
     * JComboBox with a list of pizza ingredients
     * 
     * @param ingredients
     */
    public PizzaIngredientComboBox(final List<T> ingredients) {
        super();

        for (final T ingredient : ingredients) {
            this.ingredients.add(ingredient);
            super.addItem(ingredient.getName());
        }
    }

    /**
     * @return the currently selected pizza property
     */
    public T getSelectedIngredient() {
        // TODO catch empty lists
        return ingredients.get(super.getSelectedIndex());
    }

    /**
     * @param (re)sets the list of properties
     */
    public void setIngredientList(final List<T> ingredients) {
        this.ingredients.clear();
        super.removeAllItems();

        for (final T ingredient : ingredients) {
            if (ingredient.isVisible()) {
                this.ingredients.add(ingredient);
                super.addItem(ingredient.getName());
            }
        }
    }

    /**
     * @param property
     */
    public void addIngredient(final T ingredient) {
        if (ingredient.isVisible()) {
            this.ingredients.add(ingredient);
            super.addItem(ingredient.getName());
        }
    }
}
