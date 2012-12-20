package com.notomatoesplease.userinterface.tui.widget;

import jcurses.util.Message;

public final class MessageBoxUtil {
    private static final String MSG_BOX_ERROR_TEXT = "Es ist ein Fehler aufgetreten.";
    private static final String MSG_BOX_SUCCESS_TEXT = "Aktion wurde erfolgreich durchgeführt.";
    private static final String MSG_BOX_ERROR_TITLE = "Fehler";
    private static final String MSG_BOX_SUCCESS_TITLE = "Erfolg";
    private static final String OK_LABEL = "OK";

    private MessageBoxUtil() {
    }

    public static Message getSuccessMessageBox() {
        return new Message(MSG_BOX_SUCCESS_TITLE, MSG_BOX_SUCCESS_TEXT, OK_LABEL);
    }

    public static Message getErrorMessageBox() {
        return new Message(MSG_BOX_ERROR_TITLE, MSG_BOX_ERROR_TEXT, OK_LABEL);
    }

    public static Message getErrorMessageBox(final String errorMessage) {
        return new Message(MSG_BOX_ERROR_TITLE, errorMessage, OK_LABEL);
    }

    public static Message getMessageBox(final String title, final String message) {
        return new Message(title, message, OK_LABEL);
    }
}
