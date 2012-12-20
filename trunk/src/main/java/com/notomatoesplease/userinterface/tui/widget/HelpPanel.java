package com.notomatoesplease.userinterface.tui.widget;

import static jcurses.widgets.WidgetsConstants.ALIGNMENT_CENTER;
import jcurses.widgets.GridLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Panel;

public class HelpPanel {

    private static final String HELP_LABEL_1 = "Drücke 'F1', um diese Hilfe anzuzeigen.\nDrücke 'F2', um die Sichtbarkeit der Zutaten zu bearbeiten oder neue Zutaten hinzuzufügen.\nDrücke 'F3', um eine neue Pizza zu erstellen.\n\nAllgemeine Steuerung\nMit Hilfe der Tab-Taste kann zwischen den Listen, Eingabefeldern und Schaltflächen gewechselt werden.\nSchaltflächen werden mit der ENTER-Taste ausgelöst.\nMit den Pfeiltasten können die Einträge in einer Liste markiert werden.";
    private static final String HELP_LABEL_2 = "Zutaten bearbeiten\nDie grüne Farbe eines Listeneintrags signalisiert, dass dieser Eintrag im 'Pizza erstellen'-Menü sichtbar ist.\nMit der Leerzeichen-Taste beim entsprechenden Eintrag kann dieser Zustand geändert werden.\nDie ENTER-Taste speichert die Sichtbarkeit der Einträge ab.";
    private static final String HELP_LABEL_3 = "Pizza erstellen\nDie Bestandteile der Pizza können in der jeweiligen Liste mit der Leerzeichen-Taste ausgewählt werden.\nEs ist möglich mehrere Beläge zu selektieren. Die Schaltfläche 'Pizza abschließen' zeigt die Zusammenfassung der Pizza an.";

    public static Panel getHelpPanel() {
        final Panel newHelpPanel = new Panel();

        final GridLayoutManager helpPanelLayoutManager = new GridLayoutManager(1, 3);
        newHelpPanel.setLayoutManager(helpPanelLayoutManager);

        helpPanelLayoutManager.addWidget(new Label(HELP_LABEL_1), 0, 0, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        helpPanelLayoutManager.addWidget(new Label(HELP_LABEL_2), 0, 1, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        helpPanelLayoutManager.addWidget(new Label(HELP_LABEL_3), 0, 2, 1, 1, ALIGNMENT_CENTER, ALIGNMENT_CENTER);
        return newHelpPanel;
    }
}
