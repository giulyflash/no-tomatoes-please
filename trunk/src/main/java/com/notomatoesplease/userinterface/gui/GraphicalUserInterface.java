package com.notomatoesplease.userinterface.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
        JFrame mainFrame = new JFrame("Pizza Bäcker");
        mainFrame.setSize(800,500);
        JPanel panelIngredients = new JPanel();
        JPanel panelPizza = new JPanel();

        JTabbedPane tabs = new JTabbedPane(JTabbedPane .TOP);
        tabs.add("Zutaten bearbeiten", panelIngredients);
        tabs.add("Pizza erstellen", panelPizza);

        mainFrame.add(tabs);
        mainFrame.setVisible(true);
    }

}
