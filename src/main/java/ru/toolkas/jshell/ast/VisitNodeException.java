package ru.toolkas.jshell.ast;

public class VisitNodeException extends Exception {
    public VisitNodeException() {
    }

    public VisitNodeException(String message) {
        super(message);
    }

    public VisitNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitNodeException(Throwable cause) {
        super(cause);
    }
}
