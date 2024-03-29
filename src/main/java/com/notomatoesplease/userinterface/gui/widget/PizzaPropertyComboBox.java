package com.notomatoesplease.userinterface.gui.widget;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.PizzaProperty;

public final class PizzaPropertyComboBox<T extends PizzaProperty> extends JComboBox<String> {

    private static final long serialVersionUID = -7071191321592993045L;
    private final List<T> properties = Lists.newArrayList();

    /**
     * JComboBox with a list of pizza properties
     * 
     * @param properties
     */
    public PizzaPropertyComboBox(final List<T> properties) {
        super();

        for (final T property : properties) {
            this.properties.add(property);
            super.addItem(property.getName());
        }
    }

    /**
     * @return the currently selected pizza property
     */
    public T getSelectedProperty() {
        // TODO catch empty lists
        return properties.get(super.getSelectedIndex());
    }

    /**
     * @param (re)sets the list of properties
     */
    public void setPropertyList(final List<T> properties) {
        final ActionListener[] al = super.getActionListeners();

        for (final ActionListener listener : al) {
            super.removeActionListener(listener);
        }

        this.properties.clear();
        super.removeAll();

        for (final T property : properties) {
            this.properties.add(property);
            super.addItem(property.getName());
        }

        for (final ActionListener listener : al) {
            super.addActionListener(listener);
        }
    }

    /**
     * @param property
     */
    public void addProperty(final T property) {
        this.properties.add(property);
        super.addItem(property.getName());
    }
}
