package com.notomatoesplease.userinterface.tui.widget;

import jcurses.widgets.List;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.PizzaProperty;

public class ListWidget<T extends PizzaProperty> extends List {

    private final java.util.List<T> elements = Lists.newArrayList();

    public ListWidget(final java.util.List<T> items) {
        this(items, items.size(), false);
    }

    public ListWidget(final java.util.List<T> items, final boolean multiple) {
        this(items, items.size(), multiple);
    }

    public ListWidget(final java.util.List<T> items, final int visibleSize) {
        this(items, visibleSize, false);
    }

    public ListWidget(final java.util.List<T> items, final int visibleSize, final boolean multiple) {
        super(visibleSize, multiple);
        for (final T item : items) {
            add(item);
        }
    }

    public void add(final int pos, final T item) {
        elements.add(pos, item);
        super.add(pos, propertyToString(item));
    }

    public void add(final T item) {
        add(elements.size(), item);
    }

    public T getPizzaItem(final int index) {
        return elements.get(index);
    }

    public java.util.List<T> getPizzaItems() {
        return Lists.newArrayList(elements);
    }

    public java.util.List<T> getSelectedPizzaItems() {
        final java.util.List<T> result = Lists.newArrayList();
        for (int i = 0; i < elements.size(); i++) {
            final boolean selected = isSelected(i);
            if (selected) {
                result.add(elements.get(i));
            }
        }

        return result;
    }

    public T getSelectedPizzaItem() {
        final java.util.List<T> results = getSelectedPizzaItems();
        T result = null;
        if (results.size() == 1) {
            result = results.get(0);
        }

        return result;
    }

    /**
     * Select all items in the list. Only useful when multiple items can be
     * selected at the same time.
     */
    public void selectAll() {
        if (elements.size() == 0) {
            return;
        }
        for (int i = 0; i < elements.size(); i++) {
            super.select(i);
        }
    }

    private String propertyToString(final T item) {
        final StringBuilder sb = new StringBuilder(item.getName());
        sb.append(" - ");
        sb.append(String.format(WidgetUtil.CURRENCY_FORMAT, Double.valueOf((double) item.getPrice() / (double) 100)));
        return sb.toString();
    }

}
