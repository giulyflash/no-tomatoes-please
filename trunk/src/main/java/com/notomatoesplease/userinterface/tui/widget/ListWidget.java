package com.notomatoesplease.userinterface.tui.widget;

import java.lang.reflect.Field;

import jcurses.system.CharColor;
import jcurses.system.InputChar;
import jcurses.system.Toolkit;
import jcurses.widgets.List;

import com.google.common.collect.Lists;
import com.notomatoesplease.domain.PizzaProperty;

public class ListWidget<T extends PizzaProperty> extends List {

    private final java.util.List<T> elements = Lists.newArrayList();

    private CharColor highlightColor = WidgetUtil.HIGHLIGHTED_COLOR;

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
        addAll(items);
    }

    public void add(final int pos, final T item) {
        elements.add(pos, item);
        super.add(pos, propertyToString(item));
    }

    public void addAll(final java.util.List<T> items) {
        for (final T item : items) {
            add(item);
        }
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

    private String propertyToString(final T item, final int maxWidth) {
        final StringBuilder sb = new StringBuilder(item.getName());
        sb.append(" - ");
        sb.append(String.format(WidgetUtil.CURRENCY_FORMAT, Double.valueOf((double) item.getPrice() / (double) 100)));

        while (sb.length() < maxWidth) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Color that is used for the current highlighted item in the list.
     * 
     * @param color
     *            - new color for the highlighted item.
     */
    public void setHighlightColor(final CharColor color) {
        highlightColor = color;
    }

    /**
     * Returns color that is used for the current highlighted item in the list.
     * 
     * @return color for the highlighted item
     */
    public CharColor getHighlightColor() {
        return highlightColor;
    }

    @Override
    protected void doPaint() {
        super.doPaint();
        redrawHighlightedItems();
    }

    @Override
    protected boolean handleInput(final InputChar ch) {
        final boolean result = super.handleInput(ch);
        redrawHighlightedItems();
        return result;
    }

    @Override
    protected void focus() {
        super.focus();
        redrawHighlightedItems();
    }

    /**
     * draws item with the highlighted color that is currently tracked.
     */
    private void redrawHighlightedItems() {
        final int x = getAbsoluteX() + 1;
        final int y = getAbsoluteY() + 1;
        final int width = getSize().getWidth() - 2;
        if (!elements.isEmpty()) {
            for (int i = 0; i < elements.size(); i++) {
                if (i == getTrackedItem() && hasFocus()) {
                    Toolkit.printString(propertyToString(getPizzaItem(i), width), x, y + i - getInternalStartIndex(),
                                    getHighlightColor());
                }
            }
        }
    }

    /**
     * Returns internal start index
     * 
     * @return value of start index
     */
    private int getInternalStartIndex() {
        try {
            final Field field = List.class.getDeclaredField("_startIndex");
            field.setAccessible(true);
            final Object value = field.get(this);
            field.setAccessible(false);

            if (value == null) {
                return 0;
            } else if (Integer.class.isAssignableFrom(value.getClass())) {
                return ((Integer) value).intValue();
            }
            throw new RuntimeException("Wrong value");
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * removes all elements from this list.
     */
    public void removeAll() {
        for (final T item : elements) {
            remove(propertyToString(item));
        }
        elements.clear();
    }

    public void updateList(final java.util.List<T> items) {
        removeAll();
        addAll(items);
        doRepaint();
    }

}
