package com.notomatoesplease.userinterface.tui.widget;

import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.event.WindowEvent;
import jcurses.event.WindowListener;
import jcurses.widgets.BorderPanel;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.List;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;

public class ApplicationWindow extends Window {

    private final Logic logic;
    private final List addedToppings = new List();

    public ApplicationWindow(final Logic logic, final int width, final int height) {
        super(width, height, true, "Pizza-Bäcker");
        this.logic = logic;
        final GridLayoutManager mainManager = new GridLayoutManager(1, 1);
        getRootPanel().setLayoutManager(mainManager);
        addListener(new WindowListener() {
            @Override
            public void windowChanged(final WindowEvent event) {
                if (WindowEvent.CLOSING == event.getType()) {
                    event.getSourceWindow().close();
                }
            }
        });

        // add several widgets
        final BorderPanel mainPanel = new BorderPanel();
        mainManager.addWidget(mainPanel, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                        WidgetsConstants.ALIGNMENT_CENTER);

        final GridLayoutManager manager = new GridLayoutManager(2, 4);

        mainPanel.setLayoutManager(manager);

        final List toppingList = getAvailableToppings();
        toppingList.setTitle("Belag");
        manager.addWidget(toppingList, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        final List doughList = getAvailableDoughs();
        doughList.setTitle("Teig");
        manager.addWidget(doughList, 0, 0, 1, 2, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        final List sauceList = getAvailableSauces();
        sauceList.setTitle("Sauce");
        manager.addWidget(sauceList, 0, 1, 1, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        final List sizeList = getAvailableSizes();
        sizeList.setTitle("Größe");
        manager.addWidget(sizeList, 0, 1, 1, 2, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        addedToppings.setTitle("Bereits hinzugefügte Beläge");
        addedToppings.setSelectedItemColors(ColorUtil.SELECTED_ITEM);
        manager.addWidget(addedToppings, 1, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                        WidgetsConstants.ALIGNMENT_CENTER);

    }

    private List getAvailableSizes() {
        final List list = new List(1);

        for (final Size item : logic.getSizes()) {
            list.add(item.getName());
        }
        return list;
    }

    private List getAvailableDoughs() {
        final List list = new List(1);
        for (final Dough item : logic.getDoughs()) {
            if (item.isVisible()) {
                list.add(item.getName());
            }
        }
        return list;
    }

    private List getAvailableSauces() {
        final List list = new List(1);
        for (final Sauce item : logic.getSauces()) {
            if (item.isVisible()) {
                list.add(item.getName());
            }
        }
        return list;
    }

    private List getAvailableToppings() {
        final List list = new List(1);
        list.addListener(new ItemListener() {

            @Override
            public void stateChanged(final ItemEvent event) {
                if (ItemEvent.CALLED == event.getType()) {
                    final String selectedItem = (String) event.getItem();
                    addedToppings.add(selectedItem);
                    pack();
                    paint();
                }
            }
        });
        for (final Topping item : logic.getToppings()) {
            if (item.isVisible()) {
                list.add(item.getName());
            }
        }
        return list;
    }
}
