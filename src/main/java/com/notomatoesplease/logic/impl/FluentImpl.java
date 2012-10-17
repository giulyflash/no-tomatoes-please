package com.notomatoesplease.logic.impl;

import java.util.List;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;

public class FluentImpl implements Logic {

    @Override
    public List<Size> getSizes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Dough> getDoughs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Sauce> getSauces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Topping> getToppings() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveToppings(List<Topping> toppings) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveDoughs(List<Dough> doughs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveSauces(List<Sauce> sauces) {
        // TODO Auto-generated method stub

    }

    @Override
    public Pizza createPizza(Size size, Dough dough, Sauce sauce,
            List<Topping> toppings) {
        // TODO Auto-generated method stub
        return null;
    }
}
