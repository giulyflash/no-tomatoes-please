package com.notomatoesplease.userinterface.tui;

import jcurses.event.WindowEvent;
import jcurses.event.WindowListener;
import jcurses.widgets.BorderPanel;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.List;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.userinterface.AbstractUserInterface;
import com.notomatoesplease.userinterface.UserInterface;

public class TextUserInterface extends AbstractUserInterface implements UserInterface {

    private static final Logger LOG = LoggerFactory.getLogger(TextUserInterface.class);

    public TextUserInterface(final Logic logic) {
        super(logic);
        LOG.debug("using text mode user interface");
    }

    @Override
    public void run() {
        final Window mainWindow = new Window(150, 50, true, "Pizza-Bäcker");
        final GridLayoutManager mainManager = new GridLayoutManager(1, 1);
        mainWindow.getRootPanel().setLayoutManager(mainManager);
        mainWindow.addListener(new WindowListener() {

            @Override
            public void windowChanged(final WindowEvent event) {
                if (WindowEvent.CLOSING == event.getType()) {
                    event.getSourceWindow().close();
                }
            }
        });

        final BorderPanel mainPanel = new BorderPanel();
        mainManager.addWidget(mainPanel, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER,
                        WidgetsConstants.ALIGNMENT_CENTER);

        final GridLayoutManager manager = new GridLayoutManager(2, 2);

        mainPanel.setLayoutManager(manager);

        final List sizeList = getAvailableSizes();
        sizeList.setTitle("Größe");
        manager.addWidget(sizeList, 0, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        final List doughList = getAvailableDoughs();
        doughList.setTitle("Teig");
        manager.addWidget(doughList, 0, 1, 1, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        final List sauceList = getAvailableSauces();
        sauceList.setTitle("Sauce");
        manager.addWidget(sauceList, 1, 0, 1, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        final List toppingList = getAvailableToppings();
        toppingList.setTitle("Belag");
        manager.addWidget(toppingList, 1, 1, 1, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        mainWindow.show();
    }

    private List getAvailableSizes() {
        final List list = new List(1);

        for (final Size item : getLogic().getSizes()) {
            list.add(item.getName());
        }
        return list;
    }

    private List getAvailableDoughs() {
        final List list = new List(1);
        for (final Dough item : getLogic().getDoughs()) {
            if (item.isVisible()) {
                list.add(item.getName());
            }
        }
        return list;
    }

    private List getAvailableSauces() {
        final List list = new List(1);
        for (final Sauce item : getLogic().getSauces()) {
            if (item.isVisible()) {
                list.add(item.getName());
            }
        }
        return list;
    }

    private List getAvailableToppings() {
        final List list = new List(1);
        for (final Topping item : getLogic().getToppings()) {
            if (item.isVisible()) {
                list.add(item.getName());
            }
        }
        return list;
    }

}
