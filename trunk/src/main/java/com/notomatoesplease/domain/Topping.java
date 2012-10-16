package com.notomatoesplease.domain;

import com.notomatoesplease.domain.enumeration.IngredientType;

public class Topping extends Ingredient {

    @Override
    public IngredientType getType() {
        return IngredientType.TOPPING;
    }

}
