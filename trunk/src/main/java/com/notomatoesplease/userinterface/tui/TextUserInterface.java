package com.notomatoesplease.userinterface.tui;

import jcurses.event.ActionEvent;
import jcurses.event.ActionListener;
import jcurses.system.CharColor;
import jcurses.widgets.Button;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

import com.notomatoesplease.userinterface.UserInterface;

public class TextUserInterface implements UserInterface {

    @Override
    public void show() {
        final Window w = new Window(100, 25, true, "Pizza im Text Mode!");
        final Label label = new Label("Ich bin ein Text", new CharColor(CharColor.BLACK, CharColor.GREEN));
        final Button button = new Button("Beenden");
        final ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent event) {
                w.close();
            }
        };
        button.addListener(listener);
        final DefaultLayoutManager mgr = new DefaultLayoutManager();
        mgr.bindToContainer(w.getRootPanel());
        mgr.addWidget(label, 0, 0, 100, 25, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);
        mgr.addWidget(button, 5, 5, 10, 3, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);
        w.show();
    }

}
