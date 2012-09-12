package com.notomatoesplease.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Ingredient;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.util.GsonUtil;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        System.out.println("No tomatoes, please!");

        LOG.debug("Pizza zu JSON konvertiert: {}", GsonUtil.toJson(setUpExamplePizza()));
    }

    private static Pizza setUpExamplePizza() {
        final Pizza pizza = new Pizza();

        final Ingredient dough = new Ingredient();
        dough.setName("Weizenteig");
        dough.setPrice(450);
        dough.setVisible(true);

        final Ingredient sauce = new Ingredient();
        sauce.setName("Tomatensoße");
        sauce.setPrice(70);
        sauce.setVisible(true);

        final Ingredient cheese = new Ingredient();
        cheese.setName("Käse");
        cheese.setPrice(50);
        cheese.setVisible(true);

        final Ingredient ham = new Ingredient();
        ham.setName("Schinken");
        ham.setPrice(75);
        ham.setVisible(true);

        final Ingredient olives = new Ingredient();
        olives.setName("Oliven");
        olives.setPrice(90);
        olives.setVisible(true);

        final List<Ingredient> toppings = Lists.newArrayList(cheese, ham, olives);

        pizza.setDough(dough);
        pizza.setSauce(sauce);
        pizza.setToppings(toppings);

        return pizza;
    }
}
