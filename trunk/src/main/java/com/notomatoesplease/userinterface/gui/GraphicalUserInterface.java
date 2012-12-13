package com.notomatoesplease.userinterface.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.userinterface.AbstractUserInterface;
import com.notomatoesplease.userinterface.UserInterface;
import com.notomatoesplease.userinterface.gui.widget.PizzaComboBox;
import com.notomatoesplease.userinterface.gui.widget.PizzaIngredientTable;

public class GraphicalUserInterface extends AbstractUserInterface implements UserInterface {

    private static final Logger LOG = LoggerFactory.getLogger(GraphicalUserInterface.class);

    private static final String MAIN_FRAME_NAME = "Pizza Bäcker";
    private static final String EDIT_INGRIDIENTS_TAB = "Zutaten bearbeiten";
    private static final String CREATE_PIZZA_TAB = "Pizza erstellen";
    private static final String ADD_TOPPING_BUTTON = "Belag hinzufügen";

    public GraphicalUserInterface(final Logic logic) {
        super(logic);
        LOG.debug("using graphical user interface");
    }

    private void show() {
        // Window
        List<Size> sizeList = getLogic().getSizes();
        List<Dough> doughList = getLogic().getDoughs();
        List<Sauce> sauceList = getLogic().getSauces();
        List<Topping> toppingList = getLogic().getToppings();

        PizzaComboBox<Size> sizeComboBox = new PizzaComboBox<Size>(sizeList);
        PizzaComboBox<Dough> doughComboBox = new PizzaComboBox<Dough>(doughList);
        PizzaComboBox<Sauce> sauceComboBox = new PizzaComboBox<Sauce>(sauceList);
        PizzaComboBox<Topping> toppingComboBox = new PizzaComboBox<Topping>(toppingList);
        JButton addToppingButton = new JButton(ADD_TOPPING_BUTTON);

        JPanel panelIngredients = new JPanel();



        JPanel panelPizza = new JPanel();
        panelPizza.add(sizeComboBox);
        panelPizza.add(doughComboBox);
        panelPizza.add(sauceComboBox);
        panelPizza.add(toppingComboBox);
        panelPizza.add(addToppingButton);

        Vector<String> names = new Vector<String>();
        names.add("Belag");
        names.add("Preis in €");
        names.add("Ausgewählt");
        List<Topping> zeugslist = Lists.newArrayList();
        Topping testTop = new Topping();
        testTop.setName("aaa");
        testTop.setPrice(123);
        testTop.setVisible(true);
        zeugslist.add(testTop);
        PizzaIngredientTable<Topping> toppingTable = new PizzaIngredientTable<Topping>(names, zeugslist);
        panelPizza.add(toppingTable.getScroll());

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.add(EDIT_INGRIDIENTS_TAB, panelIngredients);
        tabs.add(CREATE_PIZZA_TAB, panelPizza);

        JFrame mainFrame = new JFrame(MAIN_FRAME_NAME);
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

        addToppingButton.addActionListener(new AddToppingListener(toppingComboBox, toppingTable));
    }

    @Override
    public void run() {
        show();
    }

    private class AddToppingListener implements ActionListener {
        private final PizzaComboBox<Topping> toppingComboBox;
        private final PizzaIngredientTable<Topping> toppingTable;

        public AddToppingListener(final PizzaComboBox<Topping> toppingComboBox, final PizzaIngredientTable<Topping> toppingTable) {
            super();
            this.toppingComboBox = toppingComboBox;
            this.toppingTable = toppingTable;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            LOG.info(toppingComboBox.getSelectedProperty().toString());
            toppingTable.addIngredient(toppingComboBox.getSelectedProperty());
        }
    }
}
