package com.notomatoesplease.userinterface.gui.widget;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public final class GBCGenerator {
    private final GridBagConstraints gbc;

    public GBCGenerator() {
        gbc = new GridBagConstraints();
    }

    public GridBagConstraints getConstraints() {
        return gbc;
    }

    public GBCGenerator setFill(final int fill) {
        gbc.fill = fill;
        return this;
    }

    public GBCGenerator setPosition(final int x, final int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return this;
    }

    public GBCGenerator setGridSpan(final int width, final int height) {
        gbc.gridwidth = width;
        gbc.gridheight = height;
        return this;
    }

    public GBCGenerator setInsets(final Insets insets) {
        gbc.insets = insets;
        return this;
    }

    public GBCGenerator setAnchor(final int anchor) {
        gbc.anchor = anchor;
        return this;
    }
}
