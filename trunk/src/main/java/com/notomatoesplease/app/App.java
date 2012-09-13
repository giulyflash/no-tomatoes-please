package com.notomatoesplease.app;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;
import com.notomatoesplease.domain.Ingredient;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.util.GsonUtil;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        LOG.info("Pizza zu JSON konvertiert: {}", GsonUtil.toJson(setUpExamplePizza()));

        final Terminal t = TerminalFacade.createTextTerminal();
        t.enterPrivateMode();

        while (t.readInput() == null) {
            t.applyBackgroundColor(randomColor());
            t.applyForegroundColor(randomColor());
            t.moveCursor(randomInt(0, t.getTerminalSize().getColumns()), randomInt(0, t.getTerminalSize().getRows()));
            t.putCharacter((char) randomInt(65, 91));
            t.moveCursor(0, 0);
        }

        t.exitPrivateMode();
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

    private static int randomInt(final int low, final int high) {
        return (int) (Math.random() * (high - low) + low);
    }

    private static Terminal.Color randomColor() {
        final int pick = new Random().nextInt(Terminal.Color.values().length);
        return Terminal.Color.values()[pick];
    }

}
