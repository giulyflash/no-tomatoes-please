package com.notomatoesplease.userinterface.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.userinterface.AbstractUserInterface;
import com.notomatoesplease.userinterface.gui.widget.PizzaComboBox;
import com.notomatoesplease.userinterface.gui.widget.PizzaIngredientTable;

public class GraphicalUserInterface extends AbstractUserInterface {

    private static final Logger LOG = LoggerFactory.getLogger(GraphicalUserInterface.class);

    private static final String MAIN_FRAME_NAME = "Pizza Bäcker";
    private static final String EDIT_INGRIDIENTS_TAB = "Zutaten bearbeiten";
    private static final String CREATE_PIZZA_TAB = "Pizza erstellen";
    private static final String ADD_TOPPING_BUTTON = "Belag hinzufügen";
    private static final String ADD_DOUGH_BUTTON = "Teig hinzufügen";
    private static final String ADD_SAUCE_BUTTON = "Soße hinzufügen";
    private static final String SELECTED_COL = "Ausgewählt";
    private static final String PRICE_IN_E = "Preis in €";
    private static final String CREATE_PIZZA_BUTTON = "Pizza erstellen";
    private static final String RESULT_MESSAGE = "%d Pizza(s) backen! Preis: %.2f€";
    private static final String BILL_TITLE = "Rechnung";
    private static final String SIZE = "Größe";
    private static final String DOUGH = "Teig:";
    private static final String SAUCE = "Soße:";
    private static final String TOPPING = "Belag:";
    private static final String COUNT = "Anzahl:";
    private static final String ADDED_TOPPINGS = "Bereits vorhandene Beläge:";
    private static final String ADDED_DOUGHS = "Bereits vorhandene Teige:";
    private static final String ADDED_SAUCES = "Bereits vorhandene Soßen:";
    private static final String TOTAL_PRICE = "Gesamtpreis in Euro";
    private static final String NEW_DOUGH = "Neuer Teig:";
    private static final String NEW_SAUCE = "Neuer Soße:";
    private static final String NEW_TOPPING = "Neuer Belag:";
    private static final String SAVE_ALL = "Zutaten speichern";
    private static final String VISIBLE = "Ist sichtbar";

    public GraphicalUserInterface(final Logic logic) {
        super(logic);
        LOG.debug("using graphical user interface");
    }

    private void showMainWindow() {
        final List<Size> sizeList = getLogic().getSizes();
        final List<Dough> doughList = getLogic().getDoughs();
        final List<Sauce> sauceList = getLogic().getSauces();
        final List<Topping> toppingList = getLogic().getToppings();

        // Pizza creation tab
        final JLabel sizeLabel = new JLabel(SIZE);
        final JLabel doughLabel = new JLabel(DOUGH);
        final JLabel sauceLabel = new JLabel(SAUCE);
        final JLabel toppingLabel = new JLabel(TOPPING);
        final JLabel countLabel = new JLabel(COUNT);
        final JLabel addedToppingsLabel = new JLabel(ADDED_TOPPINGS);
        final JLabel totalPriceLabel = new JLabel(TOTAL_PRICE);
        final PizzaComboBox<Size> sizeComboBox = new PizzaComboBox<Size>(sizeList);
        final PizzaComboBox<Dough> doughComboBox = new PizzaComboBox<Dough>(doughList);
        final PizzaComboBox<Sauce> sauceComboBox = new PizzaComboBox<Sauce>(sauceList);
        final PizzaComboBox<Topping> toppingComboBox = new PizzaComboBox<Topping>(toppingList);
        final JButton addToppingButton = new JButton(ADD_TOPPING_BUTTON);
        final JButton createPizzaButton = new JButton(CREATE_PIZZA_BUTTON);
        final SpinnerNumberModel counterSpinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
        final JSpinner counterSpinner = new JSpinner(counterSpinnerModel);

        final JPanel panelIngredients = new JPanel();

        final JPanel panelPizza = new JPanel();
        panelPizza.add(sizeLabel);
        panelPizza.add(sizeComboBox);
        panelPizza.add(doughLabel);
        panelPizza.add(doughComboBox);
        panelPizza.add(sauceLabel);
        panelPizza.add(sauceComboBox);
        panelPizza.add(toppingLabel);
        panelPizza.add(toppingComboBox);
        panelPizza.add(addToppingButton);
        panelPizza.add(countLabel);
        panelPizza.add(counterSpinner);
        panelPizza.add(totalPriceLabel);
        panelPizza.add(createPizzaButton);
        panelPizza.add(addedToppingsLabel);

        final Vector<String> names = new Vector<String>();
        names.add(TOPPING);
        names.add(PRICE_IN_E);
        names.add(SELECTED_COL);
        final PizzaIngredientTable<Topping> toppingTable = new PizzaIngredientTable<Topping>(names, false);
        panelPizza.add(toppingTable.getPaneWithTable());
        // Pizza creation tab end

        // Pizza ingredient editing tab
        final JLabel newToppingLabel = new JLabel(NEW_TOPPING);
        final JLabel priceToppingLabel = new JLabel(PRICE_IN_E);
        final JLabel addedToppingLabel = new JLabel(ADDED_TOPPINGS);
        final JLabel newDoughLabel = new JLabel(NEW_DOUGH);
        final JLabel priceDoughLabel = new JLabel(PRICE_IN_E);
        final JLabel addedDoughLabel = new JLabel(ADDED_DOUGHS);
        final JLabel newSauceLabel = new JLabel(NEW_SAUCE);
        final JLabel priceSauceLabel = new JLabel(PRICE_IN_E);
        final JLabel addedSauceLabel = new JLabel(ADDED_SAUCES);
        final JTextField toppingNameField = new JTextField();
        toppingNameField.setPreferredSize(new Dimension(50, 20));
        final JTextField doughNameField = new JTextField();
        doughNameField.setPreferredSize(new Dimension(50, 20));
        final JTextField sauceNameField = new JTextField();
        sauceNameField.setPreferredSize(new Dimension(50, 20));

        final DecimalFormat priceFormat = new DecimalFormat("0.##");
        final JFormattedTextField toppingPriceField = new JFormattedTextField(priceFormat);
        toppingPriceField.setPreferredSize(new Dimension(50, 20));
        final JFormattedTextField doughPriceField = new JFormattedTextField(priceFormat);
        doughPriceField.setPreferredSize(new Dimension(50, 20));
        final JFormattedTextField saucePriceField = new JFormattedTextField(priceFormat);
        saucePriceField.setPreferredSize(new Dimension(50, 20));

        final JButton addNewToppingButton = new JButton(ADD_TOPPING_BUTTON);
        final JButton addNewDoughButton = new JButton(ADD_DOUGH_BUTTON);
        final JButton addNewSauceButton = new JButton(ADD_SAUCE_BUTTON);
        final JButton saveAllButton = new JButton(SAVE_ALL);

        final Vector<String> editToppingNames = new Vector<String>();
        editToppingNames.add(TOPPING);
        editToppingNames.add(PRICE_IN_E);
        editToppingNames.add(VISIBLE);
        final PizzaIngredientTable<Topping> editToppingTable = new PizzaIngredientTable<Topping>(editToppingNames,
                        toppingList, true);
        final Vector<String> editDoughNames = new Vector<String>();
        editDoughNames.add(DOUGH);
        editDoughNames.add(PRICE_IN_E);
        editDoughNames.add(VISIBLE);
        final PizzaIngredientTable<Dough> editDoughTable = new PizzaIngredientTable<Dough>(editDoughNames, doughList,
                        true);
        final Vector<String> editSauceNames = new Vector<String>();
        editSauceNames.add(SAUCE);
        editSauceNames.add(PRICE_IN_E);
        editSauceNames.add(VISIBLE);
        final PizzaIngredientTable<Sauce> editSauceTable = new PizzaIngredientTable<Sauce>(editSauceNames, sauceList,
                        true);

        panelIngredients.add(newToppingLabel);
        panelIngredients.add(toppingNameField);
        panelIngredients.add(priceToppingLabel);
        panelIngredients.add(toppingPriceField);
        panelIngredients.add(addNewToppingButton);
        panelIngredients.add(addedToppingLabel);

        panelIngredients.add(newDoughLabel);
        panelIngredients.add(doughNameField);
        panelIngredients.add(priceDoughLabel);
        panelIngredients.add(doughPriceField);
        panelIngredients.add(addNewDoughButton);
        panelIngredients.add(addedDoughLabel);

        panelIngredients.add(newSauceLabel);
        panelIngredients.add(sauceNameField);
        panelIngredients.add(priceSauceLabel);
        panelIngredients.add(saucePriceField);
        panelIngredients.add(addNewSauceButton);
        panelIngredients.add(addedSauceLabel);

        panelIngredients.add(editToppingTable.getPaneWithTable());
        panelIngredients.add(editDoughTable.getPaneWithTable());
        panelIngredients.add(editSauceTable.getPaneWithTable());

        panelIngredients.add(saveAllButton);
        // Pizza ingredient editing tab end

        final JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.add(EDIT_INGRIDIENTS_TAB, panelIngredients);
        tabs.add(CREATE_PIZZA_TAB, panelPizza);

        final JFrame mainFrame = new JFrame(MAIN_FRAME_NAME);
        mainFrame.setSize(800, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainFrame.add(tabs);

        // Listener
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                System.exit(0);
            }
        });

        addToppingButton.addActionListener(new AddToppingListener(toppingComboBox, toppingTable));
        final GetListListener getListener = new GetListListener(toppingTable, sizeComboBox, doughComboBox,
                        sauceComboBox, mainFrame, counterSpinner);
        createPizzaButton.addActionListener(getListener);
    }

    @Override
    public void run() {
        showMainWindow();
    }

    /**
     * @author admin simple action listener to add toppings to topping list
     */
    private class AddToppingListener implements ActionListener {
        private final PizzaComboBox<Topping> toppingComboBox;
        private final PizzaIngredientTable<Topping> toppingTable;

        public AddToppingListener(final PizzaComboBox<Topping> toppingComboBox,
                        final PizzaIngredientTable<Topping> toppingTable) {
            super();
            this.toppingComboBox = toppingComboBox;
            this.toppingTable = toppingTable;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            LOG.debug("add toppping to pizza: {}", toppingComboBox.getSelectedProperty().toString());
            toppingTable.addIngredient(toppingComboBox.getSelectedProperty());
        }
    }

    /**
     * @author admin simple action listener to create pizza from checkboxes and
     *         topping lists
     */
    private class GetListListener implements ActionListener {
        private final PizzaIngredientTable<Topping> toppingTable;
        private final PizzaComboBox<Size> sizeComboBox;
        private final PizzaComboBox<Dough> doughComboBox;
        private final PizzaComboBox<Sauce> sauceComboBox;
        private final JFrame mainFrame;
        private final JSpinner counterSpinner;

        public GetListListener(final PizzaIngredientTable<Topping> toppingTable,
                        final PizzaComboBox<Size> sizeComboBox, final PizzaComboBox<Dough> doughComboBox,
                        final PizzaComboBox<Sauce> sauceComboBox, final JFrame mainFrame, final JSpinner counterSpinner) {
            this.toppingTable = toppingTable;
            this.sizeComboBox = sizeComboBox;
            this.doughComboBox = doughComboBox;
            this.sauceComboBox = sauceComboBox;
            this.mainFrame = mainFrame;
            this.counterSpinner = counterSpinner;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            final List<Topping> toppings = toppingTable.getCheckedIngredients();
            final Size size = sizeComboBox.getSelectedProperty();
            final Dough dough = doughComboBox.getSelectedProperty();
            final Sauce sauce = sauceComboBox.getSelectedProperty();
            final int pizzaCount = ((Integer) counterSpinner.getValue()).intValue();
            LOG.debug("current pizza properties: count={} size={} dough={} sauce={} toppings={}", new Object[] {
                            pizzaCount, size, dough, sauce, toppings });
            final Pizza pizza = getLogic().createPizza(size, dough, sauce, toppings);
            LOG.debug("generated pizza: {}", pizza);
            final String message = String
                            .format(RESULT_MESSAGE, pizzaCount, pizzaCount * pizza.getTotalPrice() / 100.0);
            JOptionPane.showMessageDialog(mainFrame, message, BILL_TITLE, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
