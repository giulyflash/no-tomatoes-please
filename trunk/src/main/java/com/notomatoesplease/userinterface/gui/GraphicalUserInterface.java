package com.notomatoesplease.userinterface.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.domain.Size;
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
        // Window
        List<Size> sizeList = getLogic().getSizes();

        String[] sizeNames = new String[sizeList.size()];
        int i = 0;

        for (Size size : sizeList) {
            sizeNames[i] = size.getName();
            i++;
        }

        JComboBox sizeComboBox = new JComboBox(sizeNames);

        JPanel panelIngredients = new JPanel();
        JPanel panelPizza = new JPanel();
        panelPizza.add(sizeComboBox);

        JTabbedPane tabs = new JTabbedPane(JTabbedPane .TOP);
        tabs.add("Zutaten bearbeiten", panelIngredients);
        tabs.add("Pizza erstellen", panelPizza);





        JFrame mainFrame = new JFrame("Pizza Bäcker");
        mainFrame.setSize(800,500);
        mainFrame.add(tabs);
        mainFrame.setVisible(true);

        // Listener
        mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    System.exit(0);
                }
            }
        );
    }
}
