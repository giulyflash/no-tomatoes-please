package com.notomatoesplease.userinterface.tui.widget;

import static jcurses.widgets.WidgetsConstants.ALIGNMENT_CENTER;

import java.util.List;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.widgets.Button;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Panel;
import jcurses.widgets.TextField;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;

public final class ManageIngredientsPanel {

    private static final String EXISTING_SAUCES_TITLE = "Bereits vorhandene Saucen";
    private static final String EXISTING_DOUGHS_TITLE = "Bereits vorhandene Teige";
    private static final String EXISTING_TOPPINGS_TITLE = "Bereits vorhandene Beläge";
    private static final String SAVE_SAUCE_BTN = "Sauce speichern";
    private static final String SAVE_DOUGH_BTN = "Teig speichern";
    private static final String SAVE_TOPPING_BTN = "Belag speichern";
    private static final String PRICE_LABEL = "Preis in Cent:";
    private static final String NEW_SAUCE_LABEL = "Neue Sauce:";
    private static final String NEW_DOUGH_LABEL = "Neuer Teig:";
    private static final String NEW_TOPPING_LABEL = "Neuer Belag:";

    private static ListWidget<Topping> existingToppings;
    private static ListWidget<Dough> existingDoughs;
    private static ListWidget<Sauce> existingSauces;

    private ManageIngredientsPanel() {
    }

    public static Panel getManageIngredientsPanel(final Logic logic) {
        final Panel newManageIngredientsPanel = new Panel();
        final GridLayoutManager manageIngredientsLayoutManager = new GridLayoutManager(6, 4);
        newManageIngredientsPanel.setLayoutManager(manageIngredientsLayoutManager);

        manageIngredientsLayoutManager.addWidget(new Label(NEW_TOPPING_LABEL), 0, 0, 1, 1, ALIGNMENT_CENTER,
                        ALIGNMENT_CENTER);
        final TextField newToppingName = new TextField();
        manageIngredientsLayoutManager.addWidget(newToppingName, 1, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        manageIngredientsLayoutManager.addWidget(new Label(NEW_DOUGH_LABEL), 2, 0, 1, 1, ALIGNMENT_CENTER,
                        ALIGNMENT_CENTER);
        final TextField newDoughName = new TextField();
        manageIngredientsLayoutManager.addWidget(newDoughName, 3, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        manageIngredientsLayoutManager.addWidget(new Label(NEW_SAUCE_LABEL), 4, 0, 1, 1, ALIGNMENT_CENTER,
                        ALIGNMENT_CENTER);

        final TextField newSauceName = new TextField();
        manageIngredientsLayoutManager.addWidget(newSauceName, 5, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        manageIngredientsLayoutManager
                        .addWidget(new Label(PRICE_LABEL), 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        final TextField newToppingPrice = new TextField();
        manageIngredientsLayoutManager.addWidget(newToppingPrice, 1, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        manageIngredientsLayoutManager
                        .addWidget(new Label(PRICE_LABEL), 2, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        final TextField newDoughPrice = new TextField();
        manageIngredientsLayoutManager.addWidget(newDoughPrice, 3, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        manageIngredientsLayoutManager
                        .addWidget(new Label(PRICE_LABEL), 4, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        final TextField newSaucePrice = new TextField();
        manageIngredientsLayoutManager.addWidget(newSaucePrice, 5, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Button saveToppingButton = new Button(SAVE_TOPPING_BTN);
        saveToppingButton.addListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    final Topping newTopping = new Topping();
                    newTopping.setName(newToppingName.getText());
                    newTopping.setPrice(Integer.parseInt(newToppingPrice.getText()));
                    newTopping.setVisible(true);
                    final List<Topping> toppings = logic.getToppings();
                    toppings.add(newTopping);
                    logic.saveToppings(toppings);
                    MessageBoxUtil.getSuccessMessageBox().show();
                    existingToppings.updateList(logic.getToppings());
                    for (int i = 0; i < existingToppings.getPizzaItems().size(); i++) {
                        if (existingToppings.getPizzaItem(i).isVisible()) {
                            existingToppings.select(i);
                        }
                    }
                } catch (final NumberFormatException ex) {
                    MessageBoxUtil.getErrorMessageBox().show();
                }
            }
        });
        manageIngredientsLayoutManager.addWidget(saveToppingButton, 0, 2, 2, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Button newSaveDoughButton = new Button(SAVE_DOUGH_BTN);
        newSaveDoughButton.addListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    final Dough newDough = new Dough();
                    newDough.setName(newDoughName.getText());
                    newDough.setPrice(Integer.parseInt(newDoughPrice.getText()));
                    newDough.setVisible(true);
                    final List<Dough> doughs = logic.getDoughs();
                    doughs.add(newDough);
                    logic.saveDoughs(doughs);
                    MessageBoxUtil.getSuccessMessageBox().show();
                    existingDoughs.updateList(logic.getDoughs());
                    for (int i = 0; i < existingDoughs.getPizzaItems().size(); i++) {
                        if (existingDoughs.getPizzaItem(i).isVisible()) {
                            existingDoughs.select(i);
                        }
                    }
                } catch (final NumberFormatException ex) {
                    MessageBoxUtil.getErrorMessageBox().show();
                }
            }
        });
        manageIngredientsLayoutManager.addWidget(newSaveDoughButton, 2, 2, 2, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Button newSaveSauceButton = new Button(SAVE_SAUCE_BTN);
        newSaveSauceButton.addListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    final Sauce newSauce = new Sauce();
                    newSauce.setName(newSauceName.getText());
                    newSauce.setPrice(Integer.parseInt(newSaucePrice.getText()));
                    newSauce.setVisible(true);
                    final List<Sauce> sauces = logic.getSauces();
                    sauces.add(newSauce);
                    logic.saveSauces(sauces);
                    MessageBoxUtil.getSuccessMessageBox().show();
                    existingSauces.updateList(logic.getSauces());
                    for (int i = 0; i < existingSauces.getPizzaItems().size(); i++) {
                        if (existingSauces.getPizzaItem(i).isVisible()) {
                            existingSauces.select(i);
                        }
                    }
                } catch (final NumberFormatException ex) {
                    MessageBoxUtil.getErrorMessageBox().show();
                }
            }
        });
        manageIngredientsLayoutManager.addWidget(newSaveSauceButton, 4, 2, 2, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        existingToppings = new ListWidget<Topping>(logic.getToppings(), true);
        existingToppings.setTitle(EXISTING_TOPPINGS_TITLE);
        existingToppings.setSelectedItemColors(WidgetUtil.SELECT_ITEM_COLOR);
        existingToppings.setTitleColors(WidgetUtil.TITLE_COLOR);
        for (int i = 0; i < existingToppings.getPizzaItems().size(); i++) {
            if (existingToppings.getPizzaItem(i).isVisible()) {
                existingToppings.select(i);
            }
        }
        existingToppings.addListener(new ItemListener() {
            @Override
            public void stateChanged(final ItemEvent event) {
                if (ItemEvent.CALLED == event.getType()) {
                    final List<Topping> selectedToppings = existingToppings.getSelectedPizzaItems();
                    final List<Topping> allToppings = existingToppings.getPizzaItems();
                    for (final Topping topping : allToppings) {
                        topping.setVisible(selectedToppings.contains(topping));
                    }
                    logic.saveToppings(allToppings);
                    MessageBoxUtil.getSuccessMessageBox().show();
                }
            }
        });
        manageIngredientsLayoutManager.addWidget(existingToppings, 0, 3, 2, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        existingDoughs = new ListWidget<Dough>(logic.getDoughs(), true);
        existingDoughs.setTitle(EXISTING_DOUGHS_TITLE);
        existingDoughs.setSelectedItemColors(WidgetUtil.SELECT_ITEM_COLOR);
        existingDoughs.setTitleColors(WidgetUtil.TITLE_COLOR);
        for (int i = 0; i < existingDoughs.getPizzaItems().size(); i++) {
            if (existingDoughs.getPizzaItem(i).isVisible()) {
                existingDoughs.select(i);
            }
        }
        existingDoughs.addListener(new ItemListener() {
            @Override
            public void stateChanged(final ItemEvent event) {
                if (ItemEvent.CALLED == event.getType()) {
                    final List<Dough> selectedDoughs = existingDoughs.getSelectedPizzaItems();
                    final List<Dough> allDoughs = existingDoughs.getPizzaItems();
                    for (final Dough dough : allDoughs) {
                        dough.setVisible(selectedDoughs.contains(dough));
                    }
                    logic.saveDoughs(allDoughs);
                    MessageBoxUtil.getSuccessMessageBox().show();
                }
            }
        });
        manageIngredientsLayoutManager.addWidget(existingDoughs, 2, 3, 2, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        existingSauces = new ListWidget<Sauce>(logic.getSauces(), true);
        existingSauces.setTitle(EXISTING_SAUCES_TITLE);
        existingSauces.setSelectedItemColors(WidgetUtil.SELECT_ITEM_COLOR);
        existingSauces.setTitleColors(WidgetUtil.TITLE_COLOR);
        for (int i = 0; i < existingSauces.getPizzaItems().size(); i++) {
            if (existingSauces.getPizzaItem(i).isVisible()) {
                existingSauces.select(i);
            }
        }
        existingSauces.addListener(new ItemListener() {
            @Override
            public void stateChanged(final ItemEvent event) {
                if (ItemEvent.CALLED == event.getType()) {
                    final List<Sauce> selectedSauces = existingSauces.getSelectedPizzaItems();
                    final List<Sauce> allSauces = existingSauces.getPizzaItems();
                    for (final Sauce dough : allSauces) {
                        dough.setVisible(selectedSauces.contains(dough));
                    }
                    logic.saveSauces(allSauces);
                    MessageBoxUtil.getSuccessMessageBox().show();
                }
            }
        });
        manageIngredientsLayoutManager.addWidget(existingSauces, 4, 3, 2, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        return newManageIngredientsPanel;
    }
}
