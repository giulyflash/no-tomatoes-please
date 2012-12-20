package com.notomatoesplease.userinterface.tui.widget;

import static com.notomatoesplease.util.PizzaUtil.DOUGH_UTIL;
import static com.notomatoesplease.util.PizzaUtil.SAUCE_UTIL;
import static com.notomatoesplease.util.PizzaUtil.TOPPING_UTIL;
import static jcurses.widgets.WidgetsConstants.ALIGNMENT_CENTER;
import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.util.Message;
import jcurses.widgets.Button;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Panel;
import jcurses.widgets.TextField;

import com.google.common.base.Joiner;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;

public final class NewPizzaPanel {

    private static final String MSG_BOX_NO_TOPPINGS_LABEL = "Kein Belag ausgewählt";
    private static final String MSG_BOX_NO_SAUCE_LABEL = "Keine Sauce ausgewählt";
    private static final String MSG_BOX_NO_DOUGH_LABEL = "Kein Teig ausgewählt";
    private static final String MSG_BOX_NO_SIZE_LABEL = "Keine Größe ausgewählt";
    private static final String MSG_BOX_TOPPINGS_LABEL = "Belag: ";
    private static final String MSG_BOX_SAUCE_LABEL = "Sauce: ";
    private static final String MSG_BOX_DOUGH_LABEL = "Teig: ";
    private static final String MSG_BOX_SIZE_LABEL = "Größe: ";
    private static final String MSG_BOX_QUANTITY_LABEL = "Anzahl: ";
    private static final String NEW_LINE = "\n";
    private static final String TITLE_SIZE = "Größe";
    private static final String TITLE_DOUGH = "Teig";
    private static final String TITLE_SAUCE = "Sauce";
    private static final String TITLE_TOPPINGS = "Belag";
    private static final String DEFAULT_QUANTITY = "1";
    private static final String TOTAL_PRICE_BTN = "Pizza abschließen";
    private static final String TOTAL_PRICE = "Gesamtpreis: ";
    private static final String TOTAL_PRICE_MSG_BOX_TITLE = "Rechnung";

    private static ListWidget<Size> sizeList = null;
    private static ListWidget<Dough> doughList = null;
    private static ListWidget<Sauce> sauceList = null;
    private static ListWidget<Topping> toppingList = null;
    private static TextField pizzaQuantity = null;

    private NewPizzaPanel() {
    }

    public static Panel getNewPizzaPanel(final Logic logic) {
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
                    MessageBoxUtil.getErrorMessageBox(MSG_BOX_NO_SIZE_LABEL).show();
                    return;
                }

                final Dough selectedDough = doughList.getSelectedPizzaItem();
                if (selectedDough == null) {
                    MessageBoxUtil.getErrorMessageBox(MSG_BOX_NO_DOUGH_LABEL).show();
                    return;
                }

                final Sauce selectedSauce = sauceList.getSelectedPizzaItem();
                if (selectedSauce == null) {
                    MessageBoxUtil.getErrorMessageBox(MSG_BOX_NO_SAUCE_LABEL).show();
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
                messageBoxText.append(TOTAL_PRICE);
                messageBoxText.append(String.format(WidgetUtil.CURRENCY_FORMAT, totalPrice));
                messageBoxText.append(NEW_LINE);
                messageBoxText.append(MSG_BOX_QUANTITY_LABEL);
                messageBoxText.append(quantity);
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

                final Message msg = MessageBoxUtil.getMessageBox(TOTAL_PRICE_MSG_BOX_TITLE, messageBoxText.toString());
                msg.setBorderColors(WidgetUtil.MSG_BOX_COLOR);
                msg.setTitleColors(WidgetUtil.MSG_BOX_COLOR);
                msg.show();
            }
        });
        createPizzaLayoutManager.addWidget(totalPriceButton, 2, 2, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        return newPizzaPanel;
    }

}
