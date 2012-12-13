package com.notomatoesplease.userinterface.gui.widget;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;
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

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.Ingredient;

/**
 *
 */

/**
 * @author david.schmidt
 *
 */
public class PizzaIngredientTable<T extends Ingredient> {
    private final JScrollPane scroll;
    private final JTable table;
    private final List<T> ingredients;
    private final Vector<Object> tableData = new Vector<Object>();
    private final EachRowEditor rowEditor;
    private final EachRowRenderer rowRenderer;
    private final CheckBoxRenderer checkBoxRenderer;
    private final DefaultCellEditor checkBoxEditor;

    public PizzaIngredientTable(final Vector<String> columnNames) {
        this(columnNames, new Vector<T>());
    }

    public PizzaIngredientTable(final Vector<String> columnNames, final List<T> ingredients) {
        this.ingredients = Lists.newArrayList(ingredients);

        @SuppressWarnings("serial")
        DefaultTableModel dm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                if (column == 2) {
                    return true;
                }
                return false;
            }
        };

        for (T ingredient : ingredients) {
            Vector<Object> dataRow = new Vector<Object>();
            dataRow.add(ingredient.getName());
            dataRow.add(Double.valueOf(ingredient.getPrice() / 100.0));
            // TODO think about this boolean
            dataRow.add(Boolean.valueOf(ingredient.isVisible()));

            tableData.add(dataRow);
        }

        dm.setDataVector(tableData, columnNames);

        checkBoxRenderer = new CheckBoxRenderer();
        rowRenderer = new EachRowRenderer();
        JCheckBox checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(JLabel.CENTER);
        checkBoxEditor = new DefaultCellEditor(checkBox);
        table = new JTable(dm);
        rowEditor = new EachRowEditor(table);

        for (int i = 0; i < ingredients.size(); i++) {
            rowRenderer.add(i, checkBoxRenderer);
            rowEditor.setEditorAt(i, checkBoxEditor);
        }

        // end
        table.getColumn(columnNames.get(2)).setCellRenderer(rowRenderer);
        table.getColumn(columnNames.get(2)).setCellEditor(rowEditor);

        scroll = new JScrollPane(table);
    }

    public void addIngredient(final T ingredient) {
        Vector<Object> dataRow = new Vector<Object>();
        dataRow.add(ingredient.getName());
        dataRow.add(Double.valueOf(ingredient.getPrice() / 100.0));
        // TODO think about this boolean
        dataRow.add(Boolean.valueOf(ingredient.isVisible()));
        tableData.add(dataRow);
        rowRenderer.add(ingredients.size(), checkBoxRenderer);
        rowEditor.setEditorAt(ingredients.size(), checkBoxEditor);
        ingredients.add(ingredient);
    }

    public JScrollPane getScroll() {
        return scroll;
    }

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
            // editor = (TableCellEditor)editors.get(new Integer(row));
            // if (editor == null) {
            // editor = defaultEditor;
            // }
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

    private class EachRowRenderer implements TableCellRenderer {
        protected Hashtable<Integer, TableCellRenderer> renderers;

        protected TableCellRenderer renderer, defaultRenderer;

        public EachRowRenderer() {
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

    @SuppressWarnings("serial")
    private class CheckBoxRenderer extends JCheckBox implements
            TableCellRenderer {
        CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value, final boolean isSelected, final boolean hasFocus, final int row,
                final int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                // super.setBackground(table.getSelectionBackground());
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
