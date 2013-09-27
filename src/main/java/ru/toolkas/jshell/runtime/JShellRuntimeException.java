package ru.toolkas.jshell.runtime;

public class JShellRuntimeException extends Exception {
    public JShellRuntimeException() {
    }

    public JShellRuntimeException(String message) {
        super(message);
    }

    public JShellRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JShellRuntimeException(Throwable cause) {
        super(cause);
    }
}
