package com.notomatoesplease.userinterface.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.userinterface.AbstractUserInterface;
import com.notomatoesplease.userinterface.UserInterface;

public class GraphicalUserInterface extends AbstractUserInterface implements UserInterface {

    private static final Logger LOG = LoggerFactory.getLogger(GraphicalUserInterface.class);

    public GraphicalUserInterface(final Logic logic) {
        super(logic);
        LOG.debug("using graphical user interface");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }

}
