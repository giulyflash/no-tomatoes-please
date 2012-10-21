package com.notomatoesplease.app;

import static com.notomatoesplease.util.CommandLineUtil.SHORT_INTERFACE;
import static com.notomatoesplease.util.CommandLineUtil.SHORT_LOGIC;
import static com.notomatoesplease.util.CommandLineUtil.SHORT_PERSISTENCE;
import static com.notomatoesplease.util.CommandLineUtil.getLogic;
import static com.notomatoesplease.util.CommandLineUtil.getPersistence;
import static com.notomatoesplease.util.CommandLineUtil.getUserInterface;

import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.persistence.Persistence;
import com.notomatoesplease.userinterface.UserInterface;
import com.notomatoesplease.util.CommandLineUtil;

public class App {

    private final static Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        final Options options = new Options();
        options.addOption(CommandLineUtil.createOptionUserInterface());
        options.addOption(CommandLineUtil.createOptionPersistence());
        options.addOption(CommandLineUtil.createOptionLogic());
        final HelpFormatter formatter = new HelpFormatter();

        try {
            final Parser parser = new GnuParser();
            final CommandLine line = parser.parse(options, args);

            final Map<Character, String> values = CommandLineUtil.getCommandLineValues(line);

            final Persistence persistence = getPersistence(values.get(SHORT_PERSISTENCE));
            final Logic logic = getLogic(values.get(SHORT_LOGIC), persistence);
            final UserInterface userInterface = getUserInterface(values.get(SHORT_INTERFACE), logic);

            userInterface.run();

        } catch (final ParseException ex) {
            System.out.println(ex.getMessage());
            LOG.error(ex.getMessage(), ex);
            formatter.printHelp("notomatoesplease", options);
        } catch (final IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            LOG.error(ex.getMessage(), ex);
        }
    }

}
