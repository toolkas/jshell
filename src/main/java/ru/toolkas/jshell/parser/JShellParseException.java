package ru.toolkas.jshell.parser;

public class JShellParseException extends Exception {
    public JShellParseException() {
    }

    public JShellParseException(String message) {
        super(message);
    }

    public JShellParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JShellParseException(Throwable cause) {
        super(cause);
    }
}
