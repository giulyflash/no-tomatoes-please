package com.notomatoesplease.userinterface.gui.widget;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.notomatoesplease.domain.Ingredient;

/**
 * @author david.schmidt
 *
 */
public class PizzaIngredientTable<T extends Ingredient> {
    private final JScrollPane scrollPane;
    private final JTable table;
    private final List<T> ingredients;
    private final Set<String> usedNames = Sets.newHashSet();
    private final EachRowEditor rowEditor;
    private final EachRowRenderer rowRenderer = new EachRowRenderer();
    private final CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
    private final DefaultCellEditor checkBoxEditor;

    @SuppressWarnings("serial")
    private final DefaultTableModel dm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(final int row, final int column) {
            return column == 2;
        }
    };

    private final Predicate<T> predicate = new Predicate<T>() {
        @Override
        public boolean apply(final T item) {
            return item.isVisible();
        }
    };

    /**
     * @param columnNames
     * @param showInvisible
     */
    public PizzaIngredientTable(final Vector<String> columnNames) {
        this(columnNames, new Vector<T>());
    }

    /**
     * @param columnNames
     * @param ingredients
     * @param showInvisible
     */
    public PizzaIngredientTable(final Vector<String> columnNames, final List<T> ingredients) {
        Vector<Object> tableData = new Vector<Object>();
        this.ingredients = Lists.newArrayList(ingredients);

        for (T ingredient : this.ingredients) {
            Vector<Object> dataRow = new Vector<Object>();
            dataRow.add(ingredient.getName());
            dataRow.add(String.format("%.2f€", ingredient.getPrice() / 100.0));
            dataRow.add(Boolean.valueOf(ingredient.isVisible()));
            tableData.add(dataRow);
            usedNames.add(ingredient.getName());
        }

        dm.setDataVector(tableData, columnNames);

        JCheckBox checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(JLabel.CENTER);
        checkBoxEditor = new DefaultCellEditor(checkBox);
        table = new JTable(dm);
        rowEditor = new EachRowEditor(table);


        for (int i = 0; i < this.ingredients.size(); i++) {
            rowRenderer.add(i, checkBoxRenderer);
            rowEditor.setEditorAt(i, checkBoxEditor);
        }

        table.getColumn(columnNames.get(2)).setCellRenderer(rowRenderer);
        table.getColumn(columnNames.get(2)).setCellEditor(rowEditor);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(240, 200));
    }

    /**
     * @param cellEditorListener
     */
    public void addCellEditorListener(final CellEditorListener cellEditorListener) {
        Preconditions.checkNotNull(cellEditorListener);
        checkBoxEditor.addCellEditorListener(cellEditorListener);
    }

    /**
     * @param ingredient
     */
    public void addIngredient(final T ingredient) {
        Preconditions.checkNotNull(ingredient, "missing ingredient");

        if (! usedNames.contains(ingredient.getName())) {
            Vector<Object> dataRow = new Vector<Object>();
            dataRow.add(ingredient.getName());
            dataRow.add(String.format("%.2f€", ingredient.getPrice() / 100.0));
            dataRow.add(Boolean.TRUE);
            dm.addRow(dataRow);
            rowRenderer.add(ingredients.size(), checkBoxRenderer);
            rowEditor.setEditorAt(ingredients.size(), checkBoxEditor);
            ingredients.add(ingredient);
            usedNames.add(ingredient.getName());
        }
    }

    /**
     * removes all ingredients from this table
     */
    public void clearAll() {
        ingredients.clear();
        usedNames.clear();
        dm.setRowCount(0);
    }

    /**
     * @return return only the selected ingredients from this table
     */
    public List<T> getCheckedIngredients() {
        updateVisibilityFlag();
        return Lists.newArrayList(Iterables.filter(ingredients, predicate));
    }

    /**
     * @return all ingredients in this table
     */
    public List<T> getAllIngredients() {
        updateVisibilityFlag();
        return Lists.newArrayList(ingredients);
    }

    /**
     * @return the scroll pane with the table
     */
    public JScrollPane getPaneWithTable() {
        return scrollPane;
    }

    /**
     *
     */
    private void updateVisibilityFlag() {
        int i = 0;

        for (T ingredient : ingredients) {
            ingredient.setVisible(((Boolean) table.getValueAt(i, 2)).booleanValue());
            i++;
        }
    }

    /**
     * @author david.schmidt
     *
     */
    private class EachRowEditor implements TableCellEditor {
        protected Hashtable<Integer, TableCellEditor> editors;
        protected TableCellEditor editor, defaultEditor;
        JTable table;

        /**
         * Constructs a EachRowEditor. create default editor
         *
         * @see TableCellEditor
         * @see DefaultCellEditor
         */
        public EachRowEditor(final JTable table) {
            this.table = table;
            editors = new Hashtable<Integer, TableCellEditor>();
            defaultEditor = new DefaultCellEditor(new JTextField());
        }

        /**
         * @param row
         *            table row
         * @param editor
         *            table cell editor
         */
        public void setEditorAt(final int row, final TableCellEditor editor) {
            editors.put(new Integer(row), editor);
        }

        @Override
        public Component getTableCellEditorComponent(final JTable table,
                final Object value, final boolean isSelected, final int row, final int column) {
            return editor.getTableCellEditorComponent(table, value, isSelected,
                    row, column);
        }

        @Override
        public Object getCellEditorValue() {
            return editor.getCellEditorValue();
        }

        @Override
        public boolean stopCellEditing() {
            return editor.stopCellEditing();
        }

        @Override
        public void cancelCellEditing() {
            editor.cancelCellEditing();
        }

        @Override
        public boolean isCellEditable(final EventObject anEvent) {
            selectEditor((MouseEvent) anEvent);
            return editor.isCellEditable(anEvent);
        }

        @Override
        public void addCellEditorListener(final CellEditorListener l) {
            editor.addCellEditorListener(l);
        }

        @Override
        public void removeCellEditorListener(final CellEditorListener l) {
            editor.removeCellEditorListener(l);
        }

        @Override
        public boolean shouldSelectCell(final EventObject anEvent) {
            selectEditor((MouseEvent) anEvent);
            return editor.shouldSelectCell(anEvent);
        }

        protected void selectEditor(final MouseEvent e) {
            int row;
            if (e == null) {
                row = table.getSelectionModel().getAnchorSelectionIndex();
            }
            else {
                row = table.rowAtPoint(e.getPoint());
            }
            editor = editors.get(new Integer(row));
            if (editor == null) {
                editor = defaultEditor;
            }
        }
    }

    /**
     * @author david.schmidt
     *
     */
    private class EachRowRenderer implements TableCellRenderer {
        private final Hashtable<Integer, TableCellRenderer> renderers;
        private TableCellRenderer renderer;
        private final TableCellRenderer defaultRenderer;

        private EachRowRenderer() {
            renderers = new Hashtable<Integer, TableCellRenderer>();
            defaultRenderer = new DefaultTableCellRenderer();
        }

        public void add(final int row, final TableCellRenderer renderer) {
            renderers.put(new Integer(row), renderer);
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value, final boolean isSelected, final boolean hasFocus, final int row,
                final int column) {
            renderer = renderers.get(new Integer(row));
            if (renderer == null) {
                renderer = defaultRenderer;
            }
            return renderer.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
        }
    }

    /**
     * @author david.schmidt
     *
     */
    @SuppressWarnings("serial")
    private class CheckBoxRenderer extends JCheckBox implements
            TableCellRenderer {
        private CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value, final boolean isSelected, final boolean hasFocus, final int row,
                final int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            }
            else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }

            setSelected((value != null && ((Boolean) value).booleanValue()));
            return this;
        }
    }
}
