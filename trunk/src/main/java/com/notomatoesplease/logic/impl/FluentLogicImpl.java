package com.notomatoesplease.logic.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.AbstractLogic;
import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.persistence.Persistence;
import com.notomatoesplease.util.PizzaBakery;

public class FluentLogicImpl extends AbstractLogic implements Logic {

    private static final Logger LOG = LoggerFactory.getLogger(FluentLogicImpl.class);

    public FluentLogicImpl(final Persistence persistence) {
        super(persistence);
        LOG.debug("using fluent interface logic");
    }

    @Override
    public List<Size> getSizes() {
        return getPersistence().getSizes();
    }

    @Override
    public List<Dough> getDoughs() {
        return getPersistence().getDoughs();
    }

    @Override
    public List<Sauce> getSauces() {
        return getPersistence().getSauces();
    }

    @Override
    public List<Topping> getToppings() {
        return getPersistence().getToppings();
    }

    @Override
    public void saveToppings(final List<Topping> toppings) {
        getPersistence().saveToppings(toppings);
    }

    @Override
    public void saveDoughs(final List<Dough> doughs) {
        getPersistence().saveDoughs(doughs);
    }

    @Override
    public void saveSauces(final List<Sauce> sauces) {
        getPersistence().saveSauces(sauces);
    }

    @Override
    public Pizza createPizza(final Size size, final Dough dough, final Sauce sauce, final List<Topping> toppings) {
        return new PizzaBakery().takeDough(dough).rollOut(size).spread(sauce).sprinkle(toppings).bake();
    }
}
