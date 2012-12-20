package com.notomatoesplease.userinterface.tui;

import jcurses.widgets.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.userinterface.AbstractUserInterface;
import com.notomatoesplease.userinterface.tui.widget.ApplicationWindow;

public class TextUserInterface extends AbstractUserInterface {

    private static final Logger LOG = LoggerFactory.getLogger(TextUserInterface.class);

    public TextUserInterface(final Logic logic) {
        super(logic);
        LOG.debug("using text mode user interface");
    }

    @Override
    public void run() {
        final Window applicationWindow = new ApplicationWindow(getLogic(), 150, 50);
        applicationWindow.show();
    }

}
