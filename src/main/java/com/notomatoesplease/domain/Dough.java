package com.notomatoesplease.domain;

import com.notomatoesplease.domain.enumeration.IngredientType;

public class Dough extends Ingredient {

    @Override
    public IngredientType getType() {
        return IngredientType.DOUGH;
    }

}
