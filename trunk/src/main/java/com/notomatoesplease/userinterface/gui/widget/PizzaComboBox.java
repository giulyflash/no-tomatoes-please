package com.notomatoesplease.userinterface.gui.widget;

import java.util.List;

import javax.swing.JComboBox;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.PizzaProperty;

@SuppressWarnings("serial")
public final class PizzaComboBox<T extends PizzaProperty> extends JComboBox<String> {

    private final List<T> properties = Lists.newArrayList();

    public PizzaComboBox(final List<T> properties) {
        super();

        for (final T property : properties) {
            this.properties.add(property);
            super.addItem(property.getName());
        }
    }

    public T getSelectedProperty() {
        return properties.get(super.getSelectedIndex());
    }
}
