package com.notomatoesplease.app;

import java.util.List;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.event.ValueChangedEvent;
import jcurses.event.ValueChangedListener;
import jcurses.event.WindowEvent;
import jcurses.event.WindowListener;
import jcurses.system.CharColor;
import jcurses.util.Message;
import jcurses.widgets.Button;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.TextField;
import jcurses.widgets.Widget;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Ingredient;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.util.GsonUtil;

public class App extends Window implements ItemListener, ActionListener, ValueChangedListener, WindowListener,
                WidgetsConstants {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static App window = null;
    private static TextField textfield = null;
    private static Button button = null;

    public App(final int width, final int height) {
        super(width, height, true, "JCurses Test");
    }

    public static void main(final String[] args) {
        LOG.info("Pizza zu JSON konvertiert: {}", GsonUtil.toJson(setUpExamplePizza()));
        window = new App(30, 20);
        window.init();
    }

    public void init() {
        final DefaultLayoutManager mgr = new DefaultLayoutManager();
        mgr.bindToContainer(window.getRootPanel());
        mgr.addWidget(new Label("Hello World!", new CharColor(CharColor.WHITE, CharColor.GREEN)), 0, 0, 20, 10,
                        WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        textfield = new TextField(10);
        mgr.addWidget(textfield, 0, 0, 20, 20, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        button = new Button("Quit");
        mgr.addWidget(button, 0, 0, 20, 30, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        button.setShortCut('q');
        button.addListener(this);
        window.addListener(this);
        window.show();
    }

    public void actionPerformed(final ActionEvent event) {
        final Widget w = event.getSource();
        if (w == button) {
            new Message("HowTo", "You are about to quit", "OK").show();
            window.close();
        }
    }

    public void stateChanged(final ItemEvent e) {
    }

    public void valueChanged(final ValueChangedEvent e) {
    }

    public void windowChanged(final WindowEvent event) {
        if (event.getType() == WindowEvent.CLOSING) {
            event.getSourceWindow().close();
            // Toolkit.clearScreen(new CharColor(CharColor.WHITE,
            // CharColor.BLACK));
        }
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
