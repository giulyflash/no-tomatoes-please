package com.notomatoesplease.domain;

import com.notomatoesplease.domain.enumeration.IngredientType;

public class Sauce extends Ingredient {

    @Override
    public IngredientType getType() {
        return IngredientType.SAUCE;
    }
}
