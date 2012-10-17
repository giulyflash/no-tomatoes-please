package com.notomatoesplease.app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.persistence.Persistence;
import com.notomatoesplease.persistence.impl.H2PersistenceImpl;
import com.notomatoesplease.persistence.impl.JsonFilePersistenceImpl;
import com.notomatoesplease.userinterface.UserInterface;
import com.notomatoesplease.userinterface.gui.GraphicalUserInterface;
import com.notomatoesplease.userinterface.tui.TextUserInterface;

public class App {

    private final static Logger LOG = LoggerFactory.getLogger(App.class);

    private static final String JSON_PERSISTENCE = "JSON";
    private static final String DB_PERSISTENCE = "DB";
    private static final String TEXT_MODE = "TEXT";
    private static final String GRAPHICAL_MODE = "GRAPHICAL";
    private static final String FLUENT_LOGIC = "FLUENT";

    private static Persistence persistenceLayer;
    private static UserInterface userInterface;
    private static Logic logicLayer;


    public static void main(final String[] args) {
        final Options options = new Options();
        options.addOption(createOptionUserInterface());
        options.addOption(createOptionLogic());
        options.addOption(createOptionPersistence());
        final HelpFormatter formatter = new HelpFormatter();

        try {
            final Parser parser = new GnuParser();
            final CommandLine line = parser.parse(options, args);

            if (GRAPHICAL_MODE.equals(line.getOptionValue('i'))) {
                System.out.println("Es gibt ein grafisches Feuerwerk!");
                userInterface = new GraphicalUserInterface();
            } else if (TEXT_MODE.equals(line.getOptionValue('i'))) {
                System.out.println("Old skool text mode!");
                userInterface = new TextUserInterface();
            } else {
                System.err.println("Possible values for the interface mode are: GRAPHICAL or TEXT");
            }

            if (GRAPHICAL_MODE.equals(line.getOptionValue('l'))) {
                System.out.println("Die Logik fließt!");
                userInterface = new GraphicalUserInterface();
            } else {
                System.err.println("Possible values for the logic mode are: FLUENT");
            }

            if (DB_PERSISTENCE.equals(line.getOptionValue('p'))) {
                System.out.println("Mit einer fetten Datenbank dahinter!");
                persistenceLayer = new H2PersistenceImpl();
            } else if (JSON_PERSISTENCE.equals(line.getOptionValue('p'))) {
                System.out.println("Mit superschnellem JSON unter der Haube!");
                persistenceLayer = new JsonFilePersistenceImpl();
            } else {
                System.err.println("Possible values for the persistence mode are: DB or JSON");
            }

        } catch (final ParseException ex) {
            System.err.println(ex.getMessage());
            formatter.printHelp("notomatoesplease", options);
        } catch (final Exception ex) {
            LOG.error("unknown error while parsing command line", ex);
        }
    }


    private static Option createOptionUserInterface() {
        OptionBuilder.withArgName("mode");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt("interface");
        OptionBuilder.isRequired();
        OptionBuilder.withDescription("mode of the user interface. Possible values are: GRAPHICAL or TEXT");
        final Option userInterface = OptionBuilder.create("i");
        return userInterface;
    }


    private static Option createOptionLogic() {
        OptionBuilder.withArgName("mode");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt("logic");
        OptionBuilder.isRequired();
        OptionBuilder.withDescription("mode of the user interface. Possible values are: FLUENT");
        final Option userInterface = OptionBuilder.create("l");
        return userInterface;
    }


    private static Option createOptionPersistence() {
        OptionBuilder.withArgName("mode");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt("persistence");
        OptionBuilder.isRequired();
        OptionBuilder.withDescription("mode of the persistence. Possible values are: DB or JSON");
        final Option persistence = OptionBuilder.create("p");
        return persistence;
    }
}
