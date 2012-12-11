package com.notomatoesplease.userinterface.tui.widget;

import jcurses.system.CharColor;

public final class WidgetUtil {

    public static final CharColor TITLE_COLOR = new CharColor(CharColor.WHITE, CharColor.BLACK);
    public static final CharColor SELECT_ITEM_COLOR = new CharColor(CharColor.WHITE, CharColor.GREEN);
    public static final CharColor MSG_BOX_COLOR = new CharColor(CharColor.WHITE, CharColor.BLUE);
    public static final CharColor BUTTON_COLOR = new CharColor(CharColor.BLUE, CharColor.YELLOW);

    public static final String CURRENCY_FORMAT = "EUR %.2f";

    private WidgetUtil() {
        // I'm private Ryan
    }

}
