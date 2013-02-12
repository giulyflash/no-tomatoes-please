package com.notomatoesplease.util;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.notomatoesplease.logic.Logic;
import com.notomatoesplease.logic.impl.FluentLogicImpl;
import com.notomatoesplease.persistence.Persistence;
import com.notomatoesplease.persistence.impl.H2PersistenceImpl;
import com.notomatoesplease.persistence.impl.JsonFilePersistenceImpl;
import com.notomatoesplease.userinterface.UserInterface;
import com.notomatoesplease.userinterface.gui.GraphicalUserInterface;
import com.notomatoesplease.userinterface.tui.TextUserInterface;

public final class CommandLineUtil {

    private final static Logger LOG = LoggerFactory.getLogger(CommandLineUtil.class);

    public static final char SHORT_LOGIC = 'l';
    public static final char SHORT_INTERFACE = 'i';
    public static final char SHORT_PERSISTENCE = 'p';

    private static final String JSON_PERSISTENCE = "JSON";
    private static final String DB_PERSISTENCE = "DB";
    private static final String TEXT_MODE = "TEXT";
    private static final String GRAPHICAL_MODE = "GRAPHICAL";
    private static final String FLUENT_LOGIC = "FLUENT";

    private static final String POSSIBLE_INTERFACE_VALUES = MessageFormat.format(
                    "Possible values for the interface mode are: {0} or {1}", GRAPHICAL_MODE, TEXT_MODE);

    private static final String POSSIBLE_PERSISTENCE_VALUES = MessageFormat.format(
                    "Possible values for the persistence mode are: {0} or {1}", JSON_PERSISTENCE, DB_PERSISTENCE);

    private static final String POSSIBLE_LOGIC_VALUES = MessageFormat.format(
                    "Possible value for the logic mode is: {0}", FLUENT_LOGIC);

    private CommandLineUtil() {
    }

    public static Option createOptionUserInterface() {
        OptionBuilder.withArgName("mode");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt("interface");
        OptionBuilder.isRequired();
        OptionBuilder.withDescription(MessageFormat
                        .format("mode of the user interface. {0}", POSSIBLE_INTERFACE_VALUES));
        return OptionBuilder.create(SHORT_INTERFACE);
    }

    public static Option createOptionLogic() {
        OptionBuilder.withArgName("mode");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt("logic");
        OptionBuilder.withDescription(MessageFormat.format("mode of the logic. Default value is {0}. {1}",
                        FLUENT_LOGIC, POSSIBLE_LOGIC_VALUES));
        return OptionBuilder.create(SHORT_LOGIC);
    }

    public static Option createOptionPersistence() {
        OptionBuilder.withArgName("mode");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt("persistence");
        OptionBuilder.isRequired();
        OptionBuilder.withDescription(MessageFormat.format("mode of the persistence. {0}", POSSIBLE_PERSISTENCE_VALUES));
        return OptionBuilder.create(SHORT_PERSISTENCE);
    }

    public static Map<Character, String> getCommandLineValues(final CommandLine line) {
        final Map<Character, String> result = Maps.newHashMap();

        if (line.hasOption(SHORT_INTERFACE)) {
            result.put(SHORT_INTERFACE, line.getOptionValue(SHORT_INTERFACE));
        }

        if (line.hasOption(SHORT_PERSISTENCE)) {
            result.put(SHORT_PERSISTENCE, line.getOptionValue(SHORT_PERSISTENCE));
        }

        if (line.hasOption(SHORT_LOGIC)) {
            result.put(SHORT_LOGIC, line.getOptionValue(SHORT_LOGIC));
        } else {
            result.put(SHORT_LOGIC, FLUENT_LOGIC);
        }

        return result;
    }

    public static UserInterface getUserInterface(final String value, final Logic logic) throws IllegalArgumentException {
        UserInterface userInterface;
        final String optionValue = value.toUpperCase();
        if (GRAPHICAL_MODE.equals(optionValue)) {
            userInterface = new GraphicalUserInterface(logic);
        } else if (TEXT_MODE.equals(optionValue)) {
            userInterface = new TextUserInterface(logic);
        } else {
            final String message = MessageFormat.format("{0} is not a valid value! {1}", value,
                            POSSIBLE_INTERFACE_VALUES);
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }
        return userInterface;
    }

    public static Logic getLogic(final String value, final Persistence persistence) {
        Logic logic;
        final String optionValue = value.toUpperCase();
        if (FLUENT_LOGIC.equals(optionValue)) {
            logic = new FluentLogicImpl(persistence);
        } else {
            final String message = MessageFormat.format("{0} is not a valid value! {1}", value, POSSIBLE_LOGIC_VALUES);
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }
        return logic;
    }

    public static Persistence getPersistence(final String value) {
        Persistence persistenceLayer;
        final String optionValue = value.toUpperCase();
        if (DB_PERSISTENCE.equals(optionValue)) {
            persistenceLayer = new H2PersistenceImpl();
        } else if (JSON_PERSISTENCE.equals(optionValue)) {
            persistenceLayer = new JsonFilePersistenceImpl();
        } else {
            final String message = MessageFormat.format("{0} is not a valid value! {1}", value,
                            POSSIBLE_PERSISTENCE_VALUES);
            LOG.warn(message);
            throw new IllegalArgumentException(message);
        }

        return persistenceLayer;
    }
}
