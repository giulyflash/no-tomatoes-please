package com.notomatoesplease.userinterface.tui;

import jcurses.event.ItemEvent;
import jcurses.event.ItemListener;
import jcurses.system.CharColor;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.MenuList;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        final DefaultLayoutManager manager = new DefaultLayoutManager();

        final Window mainWindow = new Window(76, 46, true, "Pizza Bäcker");

        final Label labelIngredients = new Label("Zutaten ÄÖÜ äöü ß", new CharColor(CharColor.WHITE, CharColor.BLUE));
        final Label labelOrderPizza = new Label("Pizza!", new CharColor(CharColor.WHITE, CharColor.BLUE));
        labelOrderPizza.setVisible(false);

        final MenuList menu = new MenuList();
        menu.setTitle("Aktion wählen");
        menu.add("Zutaten bearbeiten");
        menu.add("Pizza bestellen");
        menu.add("Programm beenden");
        final ItemListener menuListener = new ItemListener() {

            @Override
            public void stateChanged(final ItemEvent event) {
                switch (event.getId()) {
                case 0:
                    labelOrderPizza.setVisible(labelOrderPizza.getVisible());
                    break;
                case 1:
                    labelIngredients.setVisible(labelIngredients.getVisible());
                    break;
                case 2:
                    mainWindow.close();
                    break;
                }
            }
        };
        menu.addListener(menuListener);

        final Label label = new Label("Copyright 2012", new CharColor(CharColor.WHITE, CharColor.CYAN));

        manager.bindToContainer(mainWindow.getRootPanel());

        manager.addWidget(menu, 0, 0, 70, 15, WidgetsConstants.ALIGNMENT_TOP, WidgetsConstants.ALIGNMENT_LEFT);
        manager.addWidget(labelIngredients, 30, 15, 20, 1, WidgetsConstants.ALIGNMENT_CENTER,
                        WidgetsConstants.ALIGNMENT_CENTER);
        manager.addWidget(labelOrderPizza, 30, 17, 20, 1, WidgetsConstants.ALIGNMENT_CENTER,
                        WidgetsConstants.ALIGNMENT_CENTER);
        manager.addWidget(label, 0, 43, 14, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

        mainWindow.show();
    }
}
