package com.notomatoesplease.userinterface.tui.widget;

import static jcurses.widgets.WidgetsConstants.ALIGNMENT_CENTER;
import jcurses.event.WindowEvent;
import jcurses.event.WindowListener;
import jcurses.system.InputChar;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Panel;
import jcurses.widgets.Window;

import com.notomatoesplease.logic.Logic;

public class ApplicationWindow extends Window {

    private static final String APPLICATION_TITLE = "Pizza-Bäcker";
    private static final String MANAGE_INGREDIENTS_LABEL = " Zutaten bearbeiten <F2> ";
    private static final String CREATE_PIZZA_LABEL = " Pizza erstellen <F3> ";
    private static final String HELP_LABEL = " Hilfe <F1> ";

    private final GridLayoutManager applicationLayoutManager;
    private final Logic logic;

    private Panel mainPanel = null;

    public ApplicationWindow(final Logic logic, final int width, final int height) {
        super(width, height, true, APPLICATION_TITLE);
        this.logic = logic;
        applicationLayoutManager = new GridLayoutManager(1, 2);
        getRootPanel().setLayoutManager(applicationLayoutManager);
        addListener(new WindowListener() {
            @Override
            public void windowChanged(final WindowEvent event) {
                if (WindowEvent.CLOSING == event.getType()) {
                    event.getSourceWindow().close();
                }
            }
        });

        // create navigation panel
        final Panel navigationPanel = new Panel();
        applicationLayoutManager.addWidget(navigationPanel, 0, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        final GridLayoutManager navigationLayoutManager = new GridLayoutManager(3, 1);
        navigationPanel.setLayoutManager(navigationLayoutManager);

        final Label manageIngredientsLabel = new Label(MANAGE_INGREDIENTS_LABEL);
        manageIngredientsLabel.setColors(WidgetUtil.BUTTON_COLOR);
        navigationLayoutManager.addWidget(manageIngredientsLabel, 0, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Label createPizzaLabel = new Label(CREATE_PIZZA_LABEL);
        createPizzaLabel.setColors(WidgetUtil.BUTTON_COLOR);
        navigationLayoutManager.addWidget(createPizzaLabel, 1, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        final Label helpLabel = new Label(HELP_LABEL);
        helpLabel.setColors(WidgetUtil.BUTTON_COLOR);
        navigationLayoutManager.addWidget(helpLabel, 2, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);

        // show help at start up
        mainPanel = HelpPanel.getHelpPanel();
        applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
    }

    @Override
    protected void handleInput(final InputChar inp) {
        super.handleInput(inp);
        if (inp.isSpecialCode()) {
            if (inp.getCode() == InputChar.KEY_F3) {
                applicationLayoutManager.removeWidget(mainPanel);
                mainPanel = NewPizzaPanel.getNewPizzaPanel(logic);
                applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
                pack();
                repaint();
            } else if (inp.getCode() == InputChar.KEY_F2) {
                applicationLayoutManager.removeWidget(mainPanel);
                mainPanel = ManageIngredientsPanel.getManageIngredientsPanel(logic);
                ;
                applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
                pack();
                repaint();
            } else if (inp.getCode() == InputChar.KEY_F1) {
                applicationLayoutManager.removeWidget(mainPanel);
                mainPanel = HelpPanel.getHelpPanel();
                applicationLayoutManager.addWidget(mainPanel, 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
                pack();
                repaint();
            }
        }
    }
}
