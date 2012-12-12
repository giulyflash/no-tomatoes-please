package com.notomatoesplease.userinterface.tui.widget;

import static com.notomatoesplease.util.PizzaUtil.DOUGH_UTIL;
import static com.notomatoesplease.util.PizzaUtil.SAUCE_UTIL;
import static com.notomatoesplease.util.PizzaUtil.TOPPING_UTIL;
import static jcurses.widgets.WidgetsConstants.ALIGNMENT_CENTER;

import java.util.List;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.event.WindowEvent;
import jcurses.event.WindowListener;
import jcurses.system.InputChar;
import jcurses.util.Message;
import jcurses.widgets.Button;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Panel;
import jcurses.widgets.TextField;
import jcurses.widgets.Window;

import com.google.common.base.Joiner;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;

public class ApplicationWindow extends Window {

    private static final String EXISTING_SAUCES_TITLE = "Bereits vorhandene Saucen";
    private static final String EXISTING_DOUGHS_TITLE = "Bereits vorhandene Teige";
    private static final String EXISTING_TOPPINGS_TITLE = "Bereits vorhandene Beläge";
    private static final String MSG_BOX_NO_TOPPINGS_LABEL = "Kein Belag ausgewählt";
    private static final String MSG_BOX_NO_SAUCE_LABEL = "Keine Sauce ausgewählt";
    private static final String MSG_BOX_NO_DOUGH_LABEL = "Kein Teig ausgewählt";
    private static final String MSG_BOX_NO_SIZE_LABEL = "Keine Größe ausgewählt";
    private static final String MSG_BOX_TOPPINGS_LABEL = "Belag: ";
    private static final String MSG_BOX_SAUCE_LABEL = "Sauce: ";
    private static final String MSG_BOX_DOUGH_LABEL = "Teig: ";
    private static final String MSG_BOX_SIZE_LABEL = "Größe: ";
    private static final String NEW_LINE = "\n";
    private static final String SAVE_SAUCE_BTN = "Sauce speichern";
    private static final String SAVE_DOUGH_BTN = "Teig speichern";
    private static final String SAVE_TOPPING_BTN = "Belag speichern";
    private static final String PRICE_LABEL = "Preis in Cent:";
    private static final String NEW_SAUCE_LABEL = "Neue Sauce:";
    private static final String NEW_DOUGH_LABEL = "Neuer Teig:";
    private static final String NEW_TOPPING_LABEL = "Neuer Belag:";
    private static final String APPLICATION_TITLE = "Pizza-Bäcker";
    private static final String TITLE_SIZE = "Größe";
    private static final String TITLE_DOUGH = "Teig";
    private static final String TITLE_SAUCE = "Sauce";
    private static final String TITLE_TOPPINGS = "Belag";
    private static final String DEFAULT_QUANTITY = "1";
    private static final String TOTAL_PRICE_BTN = "Pizza abschließen";
    private static final String TOTAL_PRICE = "Gesamtpreis: ";
    private static final String TOTAL_PRICE_MSG_BOX_TITLE = "Rechnung";
    private static final String OK_LABEL = "OK";
    private static final String MANAGE_INGREDIENTS_LABEL = " Zutaten bearbeiten <F2> ";
    private static final String CREATE_PIZZA_LABEL = " Pizza erstellen <F3> ";
    private static final String HELP_LABEL = " Hilfe <F1> ";
    private static final String HELP_LABEL_1 = "Drücke 'F1', um diese Hilfe anzuzeigen.";
    private static final String HELP_LABEL_2 = "Drücke 'F2', um Zutaten zu bearbeiten oder neue hinzuzufügen.";
    private static final String HELP_LABEL_3 = "Drücke 'F3', um eine neue Pizza zu erstellen.";
    private static final String MSG_BOX_ERROR_TEXT = "Es ist ein Fehler aufgetreten.";
    private static final String MSG_BOX_SUCCESS_TEXT = "Aktion wurde erfolgreich durchgeführt.";
    private static final String MSG_BOX_ERROR_TITLE = "Fehler";
    private static final String MSG_BOX_SUCCESS_TITLE = "Erfolg";

    private final GridLayoutManager applicationLayoutManager;
    private final Logic logic;

    private ListWidget<Size> sizeList = null;
    private ListWidget<Dough> doughList = null;
    private ListWidget<Sauce> sauceList = null;
    private ListWidget<Topping> toppingList = null;
    private TextField pizzaQuantity = null;
    private Panel mainPanel = null;
    private ListWidget<Topping> existingToppings;
    private ListWidget<Dough> existingDoughs;
    private ListWidget<Sauce> existingSauces;

    public ApplicationWindow(final Logic logic, final int width, final int height) {
        super(width, height, true, APPLICATION_TITLE);
        this.logic = logic;
        applicationLayoutManager = new GridLayoutManager(1, 2);
        getRootPanel().setLayoutManager(applicationLayoutManager);
        addListener(new WindowListener() {
            @Override
            public void windowChanged(final WindowEvent event) {
                if (WindowEvent.CLOSING == event.getType()) {
                    event.getSourceWindow().close();
                }
            }
        });

        // create navigation panel
        final Panel navigationPanel = new Panel();
        applicationLayoutManager.addWidget(navigationPanel, 0, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        final GridLayoutManager navigationLayoutManager = new GridLayoutManager(3, 1);
        navigationPanel.setLayoutManager(navigationLayoutManager);

        final Label manageIngredientsLabel = new Label(MANAGE_INGREDIENTS_LABEL);
        manageIngredientsLabel.setColors(WidgetUtil.BUTTON_COLOR);
        navigationLayoutManager.addWidget(manageIngredientsLabel, 0, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Label createPizzaLabel = new Label(CREATE_PIZZA_LABEL);
        createPizzaLabel.setColors(WidgetUtil.BUTTON_COLOR);
        navigationLayoutManager.addWidget(createPizzaLabel, 1, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Label helpLabel = new Label(HELP_LABEL);
        helpLabel.setColors(WidgetUtil.BUTTON_COLOR);
        navigationLayoutManager.addWidget(helpLabel, 2, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        // show help at start up
        mainPanel = getHelpPanel();
        applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
    }

    private Panel getManageIngredientsPanel() {
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
                    new Message(MSG_BOX_SUCCESS_TITLE, MSG_BOX_SUCCESS_TEXT, OK_LABEL).show();
                    existingToppings.updateList(logic.getToppings());
                    for (int i = 0; i < existingToppings.getPizzaItems().size(); i++) {
                        if (existingToppings.getPizzaItem(i).isVisible()) {
                            existingToppings.select(i);
                        }
                    }
                } catch (final NumberFormatException ex) {
                    new Message(MSG_BOX_ERROR_TITLE, MSG_BOX_ERROR_TEXT, OK_LABEL).show();
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
                    new Message(MSG_BOX_SUCCESS_TITLE, MSG_BOX_SUCCESS_TEXT, OK_LABEL).show();
                    existingDoughs.updateList(logic.getDoughs());
                    for (int i = 0; i < existingDoughs.getPizzaItems().size(); i++) {
                        if (existingDoughs.getPizzaItem(i).isVisible()) {
                            existingDoughs.select(i);
                        }
                    }
                } catch (final NumberFormatException ex) {
                    new Message(MSG_BOX_ERROR_TITLE, MSG_BOX_ERROR_TEXT, OK_LABEL).show();
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
                    new Message(MSG_BOX_SUCCESS_TITLE, MSG_BOX_SUCCESS_TEXT, OK_LABEL).show();
                    existingSauces.updateList(logic.getSauces());
                    for (int i = 0; i < existingSauces.getPizzaItems().size(); i++) {
                        if (existingSauces.getPizzaItem(i).isVisible()) {
                            existingSauces.select(i);
                        }
                    }
                } catch (final NumberFormatException ex) {
                    new Message(MSG_BOX_ERROR_TITLE, MSG_BOX_ERROR_TEXT, OK_LABEL).show();
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
                    // TODO: save visibility for toppings
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
                    // TODO: save visibility for doughs
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
                    // TODO: save visibility for sauces
                }
            }
        });
        manageIngredientsLayoutManager.addWidget(existingSauces, 4, 3, 2, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        return newManageIngredientsPanel;
    }

    private Panel getNewPizzaPanel(final Logic logic) {
        final Panel newPizzaPanel = new Panel();
        final GridLayoutManager createPizzaLayoutManager = new GridLayoutManager(3, 3);
        newPizzaPanel.setLayoutManager(createPizzaLayoutManager);

        // size selection widget
        sizeList = new ListWidget<Size>(logic.getSizes(), 7);
        sizeList.setTitle(TITLE_SIZE);
        sizeList.setSelectedItemColors(WidgetUtil.SELECT_ITEM_COLOR);
        sizeList.setTitleColors(WidgetUtil.TITLE_COLOR);
        createPizzaLayoutManager.addWidget(sizeList, 0, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        // dough selection widget
        doughList = new ListWidget<Dough>(DOUGH_UTIL.getVisibleOnly(logic.getDoughs()), 7);
        doughList.setTitle(TITLE_DOUGH);
        doughList.setSelectedItemColors(WidgetUtil.SELECT_ITEM_COLOR);
        doughList.setTitleColors(WidgetUtil.TITLE_COLOR);
        createPizzaLayoutManager.addWidget(doughList, 2, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        // sauce selection widget
        sauceList = new ListWidget<Sauce>(SAUCE_UTIL.getVisibleOnly(logic.getSauces()), 7);
        sauceList.setTitle(TITLE_SAUCE);
        sauceList.setSelectedItemColors(WidgetUtil.SELECT_ITEM_COLOR);
        sauceList.setTitleColors(WidgetUtil.TITLE_COLOR);
        createPizzaLayoutManager.addWidget(sauceList, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        // toppings selection widget
        toppingList = new ListWidget<Topping>(TOPPING_UTIL.getVisibleOnly(logic.getToppings()), 7, true);
        toppingList.setTitle(TITLE_TOPPINGS);
        toppingList.setSelectedItemColors(WidgetUtil.SELECT_ITEM_COLOR);
        toppingList.setTitleColors(WidgetUtil.TITLE_COLOR);
        createPizzaLayoutManager.addWidget(toppingList, 2, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        // quantity selection widget
        pizzaQuantity = new TextField();
        pizzaQuantity.setText(DEFAULT_QUANTITY, true);
        createPizzaLayoutManager.addWidget(new Label("Anzahl"), 0, 2, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        createPizzaLayoutManager.addWidget(pizzaQuantity, 1, 2, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Button totalPriceButton = new Button(TOTAL_PRICE_BTN);
        totalPriceButton.setShortCut('p');
        totalPriceButton.addListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {

                final Size selectedSize = sizeList.getSelectedPizzaItem();
                if (selectedSize == null) {
                    new Message(MSG_BOX_ERROR_TITLE, MSG_BOX_NO_SIZE_LABEL, OK_LABEL).show();
                    return;
                }

                final Dough selectedDough = doughList.getSelectedPizzaItem();
                if (selectedDough == null) {
                    new Message(MSG_BOX_ERROR_TITLE, MSG_BOX_NO_DOUGH_LABEL, OK_LABEL).show();
                    return;
                }

                final Sauce selectedSauce = sauceList.getSelectedPizzaItem();
                if (selectedSauce == null) {
                    new Message(MSG_BOX_ERROR_TITLE, MSG_BOX_NO_SAUCE_LABEL, OK_LABEL).show();
                    return;
                }

                final StringBuilder messageBoxText = new StringBuilder();

                final Pizza pizza = logic.createPizza(selectedSize, selectedDough, selectedSauce,
                                toppingList.getSelectedPizzaItems());

                int quantity = 1;
                try {
                    quantity = Integer.parseInt(pizzaQuantity.getText());
                } catch (final NumberFormatException ex) {
                    quantity = 1;
                }

                final Double totalPrice = Double.valueOf((double) (pizza.getTotalPrice() * quantity) / 100);
                messageBoxText.append(TOTAL_PRICE + String.format(WidgetUtil.CURRENCY_FORMAT, totalPrice));
                messageBoxText.append(NEW_LINE);
                messageBoxText.append(MSG_BOX_SIZE_LABEL);
                messageBoxText.append(pizza.getSize().getName());
                messageBoxText.append(NEW_LINE);
                messageBoxText.append(MSG_BOX_DOUGH_LABEL);
                messageBoxText.append(pizza.getDough().getName());
                messageBoxText.append(NEW_LINE);
                messageBoxText.append(MSG_BOX_SAUCE_LABEL);

                messageBoxText.append(pizza.getSauce().getName());

                messageBoxText.append(NEW_LINE);
                messageBoxText.append(MSG_BOX_TOPPINGS_LABEL);

                if (pizza.getToppings().isEmpty()) {
                    messageBoxText.append(MSG_BOX_NO_TOPPINGS_LABEL);
                } else {
                    messageBoxText.append(Joiner.on(", ").join(TOPPING_UTIL.getNames(pizza.getToppings())));
                }

                final Message msg = new Message(TOTAL_PRICE_MSG_BOX_TITLE, messageBoxText.toString(), OK_LABEL);
                msg.setBorderColors(WidgetUtil.MSG_BOX_COLOR);
                msg.setTitleColors(WidgetUtil.MSG_BOX_COLOR);
                msg.show();
            }
        });
        createPizzaLayoutManager.addWidget(totalPriceButton, 2, 2, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        return newPizzaPanel;
    }

    private Panel getHelpPanel() {
        final Panel newHelpPanel = new Panel();

        final GridLayoutManager helpPanelLayoutManager = new GridLayoutManager(1, 3);
        newHelpPanel.setLayoutManager(helpPanelLayoutManager);

        helpPanelLayoutManager.addWidget(new Label(HELP_LABEL_1), 0, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        helpPanelLayoutManager.addWidget(new Label(HELP_LABEL_2), 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        helpPanelLayoutManager.addWidget(new Label(HELP_LABEL_3), 0, 2, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        return newHelpPanel;
    }

    @Override
    protected void handleInput(final InputChar inp) {
        super.handleInput(inp);
        if (inp.isSpecialCode()) {
            if (inp.getCode() == InputChar.KEY_F3) {
                applicationLayoutManager.removeWidget(mainPanel);
                mainPanel = getNewPizzaPanel(logic);
                applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
                pack();
                repaint();
            } else if (inp.getCode() == InputChar.KEY_F2) {
                applicationLayoutManager.removeWidget(mainPanel);
                mainPanel = getManageIngredientsPanel();
                applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
                pack();
                repaint();
            } else if (inp.getCode() == InputChar.KEY_F1) {
                applicationLayoutManager.removeWidget(mainPanel);
                mainPanel = getHelpPanel();
                applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
                pack();
                repaint();
            }
        }
    }
}
