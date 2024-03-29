package com.notomatoesplease.userinterface.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.domain.Dough;
import com.notomatoesplease.domain.Pizza;
import com.notomatoesplease.domain.Sauce;
import com.notomatoesplease.domain.Size;
import com.notomatoesplease.domain.Topping;
import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.userinterface.AbstractUserInterface;
import com.notomatoesplease.userinterface.gui.widget.GBCGenerator;
import com.notomatoesplease.userinterface.gui.widget.PizzaIngredientComboBox;
import com.notomatoesplease.userinterface.gui.widget.PizzaIngredientTable;
import com.notomatoesplease.userinterface.gui.widget.PizzaPropertyComboBox;

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
    private static final String SIZE = "Größe:";
    private static final String DOUGH = "Teig:";
    private static final String SAUCE = "Soße:";
    private static final String TOPPING = "Belag:";
    private static final String COUNT = "Anzahl:";
    private static final String ADDED_TOPPINGS = "Bereits vorhandene Beläge:";
    private static final String ADDED_DOUGHS = "Bereits vorhandene Teige:";
    private static final String ADDED_SAUCES = "Bereits vorhandene Soßen:";
    private static final String TOTAL_PRICE = "Gesamtpreis in Euro:";
    private static final String NEW_DOUGH = "Neuer Teig:";
    private static final String NEW_SAUCE = "Neuer Soße:";
    private static final String NEW_TOPPING = "Neuer Belag:";
    private static final String VISIBLE = "Ist sichtbar";
    private static final int WINDOW_WIDTH = 780;
    private static final int WINDOW_HEIGHT = 400;

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
        final PizzaPropertyComboBox<Size> sizeComboBox = new PizzaPropertyComboBox<Size>(sizeList);
        final PizzaIngredientComboBox<Dough> doughComboBox = new PizzaIngredientComboBox<Dough>(doughList);
        final PizzaIngredientComboBox<Sauce> sauceComboBox = new PizzaIngredientComboBox<Sauce>(sauceList);
        final PizzaIngredientComboBox<Topping> toppingComboBox = new PizzaIngredientComboBox<Topping>(toppingList);
        final JButton addToppingButton = new JButton(ADD_TOPPING_BUTTON);
        final JButton createPizzaButton = new JButton(CREATE_PIZZA_BUTTON);
        final SpinnerNumberModel counterSpinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
        final JSpinner counterSpinner = new JSpinner(counterSpinnerModel);
        final JTextField totalPriceField = new JTextField();
        totalPriceField.setPreferredSize(new Dimension(50, 20));
        totalPriceField.setEditable(false);

        final Vector<String> names = new Vector<String>();
        names.add(TOPPING);
        names.add(PRICE_IN_E);
        names.add(SELECTED_COL);
        final PizzaIngredientTable<Topping> toppingTable = new PizzaIngredientTable<Topping>(names);

        // fill and style pizza creation panel
        final JPanel panelPizza = new JPanel();
        panelPizza.setLayout(new GridBagLayout());
        GBCGenerator gbcGenerator = new GBCGenerator();
        GridBagConstraints gbc = gbcGenerator.setFill(GridBagConstraints.HORIZONTAL)
                .setInsets(new Insets(5, 5, 5, 5))
                .getConstraints();

        gbc = gbcGenerator.setPosition(0, 0).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_END).getConstraints();
        panelPizza.add(sizeLabel, gbc);
        gbc = gbcGenerator.setPosition(1, 0).setGridSpan(3, 1).setAnchor(GridBagConstraints.LINE_START).getConstraints();
        panelPizza.add(sizeComboBox, gbc);

        gbc = gbcGenerator.setPosition(0, 1).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_END).getConstraints();
        panelPizza.add(doughLabel, gbc);
        gbc = gbcGenerator.setPosition(1, 1).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_START).setInsets(new Insets(5, 5, 5, 20)).getConstraints();
        panelPizza.add(doughComboBox, gbc);

        gbc = gbcGenerator.setPosition(0, 2).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_END).setInsets(new Insets(5, 5, 5, 5)).getConstraints();
        panelPizza.add(sauceLabel, gbc);
        gbc = gbcGenerator.setPosition(1, 2).setGridSpan(3, 1).setAnchor(GridBagConstraints.LINE_START).setInsets(new Insets(5, 5, 5, 20)).getConstraints();
        panelPizza.add(sauceComboBox, gbc);

        gbc = gbcGenerator.setPosition(0, 3).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_END).setInsets(new Insets(5, 5, 5, 5)).getConstraints();
        panelPizza.add(toppingLabel, gbc);
        gbc = gbcGenerator.setPosition(1, 3).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_START).getConstraints();
        panelPizza.add(toppingComboBox, gbc);
        gbc = gbcGenerator.setPosition(2, 3).setGridSpan(3, 1).getConstraints();
        panelPizza.add(addToppingButton, gbc);

        gbc = gbcGenerator.setPosition(0, 4).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_END).getConstraints();
        panelPizza.add(countLabel, gbc);
        gbc = gbcGenerator.setPosition(1, 4).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_START).setInsets(new Insets(5, 5, 5, 50)).getConstraints();
        panelPizza.add(counterSpinner, gbc);

        gbc = gbcGenerator.setPosition(1, 7).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_END).setInsets(new Insets(5, 5, 5, 5)).getConstraints();
        panelPizza.add(totalPriceLabel, gbc);
        gbc = gbcGenerator.setPosition(2, 7).setGridSpan(1, 1).setAnchor(GridBagConstraints.LINE_START).getConstraints();
        panelPizza.add(totalPriceField, gbc);

        gbc = gbcGenerator.setPosition(1, 8).setGridSpan(2, 1).getConstraints();
        panelPizza.add(createPizzaButton, gbc);

        gbc = gbcGenerator.setPosition(5, 0).setGridSpan(1, 1).getConstraints();
        panelPizza.add(addedToppingsLabel, gbc);
        gbc = gbcGenerator.setPosition(5, 1).setGridSpan(1, 5).getConstraints();
        panelPizza.add(toppingTable.getPaneWithTable(), gbc);
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
        toppingNameField.setPreferredSize(new Dimension(70, 20));
        final JTextField doughNameField = new JTextField();
        doughNameField.setPreferredSize(new Dimension(70, 20));
        final JTextField sauceNameField = new JTextField();
        sauceNameField.setPreferredSize(new Dimension(70, 20));

        final DecimalFormat priceFormat = new DecimalFormat("0.##");
        final JFormattedTextField toppingPriceField = new JFormattedTextField(priceFormat);
        toppingPriceField.setPreferredSize(new Dimension(70, 20));
        final JFormattedTextField doughPriceField = new JFormattedTextField(priceFormat);
        doughPriceField.setPreferredSize(new Dimension(70, 20));
        final JFormattedTextField saucePriceField = new JFormattedTextField(priceFormat);
        saucePriceField.setPreferredSize(new Dimension(70, 20));

        final JButton addNewToppingButton = new JButton(ADD_TOPPING_BUTTON);
        final JButton addNewDoughButton = new JButton(ADD_DOUGH_BUTTON);
        final JButton addNewSauceButton = new JButton(ADD_SAUCE_BUTTON);

        final Vector<String> editToppingNames = new Vector<String>();
        editToppingNames.add(TOPPING);
        editToppingNames.add(PRICE_IN_E);
        editToppingNames.add(VISIBLE);
        final PizzaIngredientTable<Topping> editToppingTable = new PizzaIngredientTable<Topping>(editToppingNames,
                        toppingList);
        final Vector<String> editDoughNames = new Vector<String>();
        editDoughNames.add(DOUGH);
        editDoughNames.add(PRICE_IN_E);
        editDoughNames.add(VISIBLE);
        final PizzaIngredientTable<Dough> editDoughTable = new PizzaIngredientTable<Dough>(editDoughNames, doughList);
        final Vector<String> editSauceNames = new Vector<String>();
        editSauceNames.add(SAUCE);
        editSauceNames.add(PRICE_IN_E);
        editSauceNames.add(VISIBLE);
        final PizzaIngredientTable<Sauce> editSauceTable = new PizzaIngredientTable<Sauce>(editSauceNames, sauceList);

        // fill and style ingredients panel
        final JPanel panelIngredients = new JPanel();
        panelIngredients.setLayout(new GridBagLayout());
        gbcGenerator = new GBCGenerator();
        gbc = gbcGenerator.setFill(GridBagConstraints.HORIZONTAL)
                .setInsets(new Insets(5, 5, 5, 5))
                .setAnchor(GridBagConstraints.LINE_START)
                .getConstraints();

        gbc = gbcGenerator.setPosition(0, 0).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(newToppingLabel, gbc);
        gbc = gbcGenerator.setPosition(1, 0).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(toppingNameField, gbc);
        gbc = gbcGenerator.setPosition(0, 1).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(priceToppingLabel, gbc);
        gbc = gbcGenerator.setPosition(1, 1).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(toppingPriceField, gbc);
        gbc = gbcGenerator.setPosition(0, 2).setGridSpan(2, 1).setInsets(new Insets(5, 5, 5, 40)).getConstraints();
        panelIngredients.add(addNewToppingButton, gbc);
        gbc = gbcGenerator.setPosition(0, 3).setGridSpan(2, 1).getConstraints();
        panelIngredients.add(addedToppingLabel, gbc);

        gbc = gbcGenerator.setPosition(4, 0).setGridSpan(1, 1).setInsets(new Insets(5, 5, 5, 5)).getConstraints();
        panelIngredients.add(newDoughLabel, gbc);
        gbc = gbcGenerator.setPosition(5, 0).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(doughNameField, gbc);
        gbc = gbcGenerator.setPosition(4, 1).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(priceDoughLabel, gbc);
        gbc = gbcGenerator.setPosition(5, 1).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(doughPriceField, gbc);
        gbc = gbcGenerator.setPosition(4, 2).setGridSpan(2, 1).setInsets(new Insets(5, 5, 5, 40)).getConstraints();
        panelIngredients.add(addNewDoughButton, gbc);
        gbc = gbcGenerator.setPosition(4, 3).setGridSpan(2, 1).getConstraints();
        panelIngredients.add(addedDoughLabel, gbc);

        gbc = gbcGenerator.setPosition(7, 0).setGridSpan(1, 1).setInsets(new Insets(5, 5, 5, 5)).getConstraints();
        panelIngredients.add(newSauceLabel, gbc);
        gbc = gbcGenerator.setPosition(8, 0).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(sauceNameField, gbc);
        gbc = gbcGenerator.setPosition(7, 1).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(priceSauceLabel, gbc);
        gbc = gbcGenerator.setPosition(8, 1).setGridSpan(1, 1).getConstraints();
        panelIngredients.add(saucePriceField, gbc);
        gbc = gbcGenerator.setPosition(7, 2).setGridSpan(2, 1).setInsets(new Insets(5, 5, 5, 40)).getConstraints();
        panelIngredients.add(addNewSauceButton, gbc);
        gbc = gbcGenerator.setPosition(7, 3).setGridSpan(2, 1).getConstraints();
        panelIngredients.add(addedSauceLabel, gbc);

        gbc = gbcGenerator.setPosition(0, 4).setGridSpan(3, 1).setInsets(new Insets(5, 5, 5, 5)).getConstraints();
        panelIngredients.add(editToppingTable.getPaneWithTable(), gbc);
        gbc = gbcGenerator.setPosition(4, 4).setGridSpan(3, 1).getConstraints();
        panelIngredients.add(editDoughTable.getPaneWithTable(), gbc);
        gbc = gbcGenerator.setPosition(7, 4).setGridSpan(3, 1).getConstraints();
        panelIngredients.add(editSauceTable.getPaneWithTable(), gbc);
        // Pizza ingredient editing tab end

        // put everything together
        final JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.add(EDIT_INGRIDIENTS_TAB, panelIngredients);
        tabs.add(CREATE_PIZZA_TAB, panelPizza);

        final JFrame mainFrame = new JFrame(MAIN_FRAME_NAME);
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainFrame.add(tabs);

        //calculate initial total price
        totalPriceField.setText(String.format("%.2f€", calculateTotalPrice(toppingTable, sizeComboBox, doughComboBox, sauceComboBox, counterSpinner)));

        // create pizza pane listener
        counterSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent arg0) {
                totalPriceField.setText(String.format("%.2f€", calculateTotalPrice(toppingTable, sizeComboBox, doughComboBox, sauceComboBox, counterSpinner)));
            }
        });

        addToppingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                totalPriceField.setText(String.format("%.2f€", calculateTotalPrice(toppingTable, sizeComboBox, doughComboBox, sauceComboBox, counterSpinner)));
            }
        });

        sizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                totalPriceField.setText(String.format("%.2f€", calculateTotalPrice(toppingTable, sizeComboBox, doughComboBox, sauceComboBox, counterSpinner)));
            }
        });

        doughComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                totalPriceField.setText(String.format("%.2f€", calculateTotalPrice(toppingTable, sizeComboBox, doughComboBox, sauceComboBox, counterSpinner)));
            }
        });

        sauceComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                totalPriceField.setText(String.format("%.2f€", calculateTotalPrice(toppingTable, sizeComboBox, doughComboBox, sauceComboBox, counterSpinner)));
            }
        });

        addToppingButton.addActionListener(new AddToppingListener(toppingComboBox, toppingTable));
        final GetListListener getListener = new GetListListener(toppingTable, sizeComboBox, doughComboBox,
                        sauceComboBox, mainFrame, counterSpinner);
        createPizzaButton.addActionListener(getListener);

        toppingTable.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                totalPriceField.setText(String.format("%.2f€", calculateTotalPrice(toppingTable, sizeComboBox, doughComboBox, sauceComboBox, counterSpinner)));
            }

            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        });

        // ingredient pane listener
        addNewSauceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (saucePriceField.getValue() != null && sauceNameField.getText().length() > 0) {
                    Sauce sauce = new Sauce();
                    sauce.setName(sauceNameField.getText());
                    sauce.setPrice((int) (((Number) saucePriceField.getValue()).doubleValue() * 100.0));
                    sauce.setVisible(true);
                    sauceComboBox.addIngredient(sauce);
                    editSauceTable.addIngredient(sauce);
                    sauceList.add(sauce);
                    getLogic().saveSauces(sauceList);
                }
            }
        });

        addNewToppingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (toppingPriceField.getValue() != null && toppingNameField.getText().length() > 0) {
                    Topping topping = new Topping();
                    topping.setName(toppingNameField.getText());
                    topping.setPrice((int) (((Number) toppingPriceField.getValue()).doubleValue() * 100.0));
                    topping.setVisible(true);
                    toppingComboBox.addIngredient(topping);
                    editToppingTable.addIngredient(topping);
                    toppingList.add(topping);
                    getLogic().saveToppings(toppingList);
                }
            }
        });

        addNewDoughButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (doughPriceField.getValue() != null && doughNameField.getText().length() > 0) {
                    Dough dough = new Dough();
                    dough.setName(doughNameField.getText());
                    dough.setPrice((int) (((Number) doughPriceField.getValue()).doubleValue() * 100.0));
                    dough.setVisible(true);
                    doughComboBox.addIngredient(dough);
                    editDoughTable.addIngredient(dough);
                    doughList.add(dough);
                    getLogic().saveDoughs(doughList);
                }
            }
        });

        editDoughTable.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                doughList.clear();
                doughList.addAll(editDoughTable.getAllIngredients());
                getLogic().saveDoughs(doughList);
                doughComboBox.setIngredientList(doughList);
            }

            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        });

        editSauceTable.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                sauceList.clear();
                sauceList.addAll(editSauceTable.getAllIngredients());
                getLogic().saveSauces(sauceList);
                sauceComboBox.setIngredientList(sauceList);
            }

            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        });

        editToppingTable.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(final ChangeEvent e) {
                toppingList.clear();
                toppingList.addAll(editToppingTable.getAllIngredients());
                getLogic().saveToppings(toppingList);
                toppingComboBox.setIngredientList(toppingList);
            }

            @Override
            public void editingCanceled(final ChangeEvent e) {
            }
        });
    }


    private final double  calculateTotalPrice(final PizzaIngredientTable<Topping> toppingTable,
            final PizzaPropertyComboBox<Size> sizeComboBox, final PizzaIngredientComboBox<Dough> doughComboBox,
            final PizzaIngredientComboBox<Sauce> sauceComboBox, final JSpinner counterSpinner) {
        final List<Topping> toppings = toppingTable.getCheckedIngredients();
        final Size size = sizeComboBox.getSelectedProperty();
        final Dough dough = doughComboBox.getSelectedIngredient();
        final Sauce sauce = sauceComboBox.getSelectedIngredient();
        final int pizzaCount = ((Integer) counterSpinner.getValue()).intValue();
        final Pizza pizza = getLogic().createPizza(size, dough, sauce, toppings);
        return pizza.getTotalPrice() * pizzaCount / 100.0;
    }


    @Override
    public void run() {
        showMainWindow();
    }

    /**
     * @author admin simple action listener to add toppings to topping list
     */
    private class AddToppingListener implements ActionListener {
        private final PizzaIngredientComboBox<Topping> toppingComboBox;
        private final PizzaIngredientTable<Topping> toppingTable;

        public AddToppingListener(final PizzaIngredientComboBox<Topping> toppingComboBox,
                        final PizzaIngredientTable<Topping> toppingTable) {
            super();
            this.toppingComboBox = toppingComboBox;
            this.toppingTable = toppingTable;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            LOG.debug("add toppping to pizza: {}", toppingComboBox.getSelectedIngredient().toString());
            toppingTable.addIngredient(toppingComboBox.getSelectedIngredient());
        }
    }

    /**
     * @author admin simple action listener to create pizza from checkboxes and
     *         topping lists
     */
    private class GetListListener implements ActionListener {
        private final PizzaIngredientTable<Topping> toppingTable;
        private final PizzaPropertyComboBox<Size> sizeComboBox;
        private final PizzaIngredientComboBox<Dough> doughComboBox;
        private final PizzaIngredientComboBox<Sauce> sauceComboBox;
        private final JFrame mainFrame;
        private final JSpinner counterSpinner;

        public GetListListener(final PizzaIngredientTable<Topping> toppingTable,
                        final PizzaPropertyComboBox<Size> sizeComboBox, final PizzaIngredientComboBox<Dough> doughComboBox,
                        final PizzaIngredientComboBox<Sauce> sauceComboBox, final JFrame mainFrame, final JSpinner counterSpinner) {
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
            final Dough dough = doughComboBox.getSelectedIngredient();
            final Sauce sauce = sauceComboBox.getSelectedIngredient();
            final int pizzaCount = ((Integer) counterSpinner.getValue()).intValue();
            LOG.debug("current pizza properties: count={} size={} dough={} sauce={} toppings={}",
                    new Object[] {pizzaCount, size, dough, sauce, toppings });
            final Pizza pizza = getLogic().createPizza(size, dough, sauce, toppings);
            LOG.debug("generated pizza: {}", pizza);
            final String message = String.format(RESULT_MESSAGE, pizzaCount, pizzaCount * pizza.getTotalPrice() / 100.0);
            JOptionPane.showMessageDialog(mainFrame, message, BILL_TITLE, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
