package ru.toolkas.jshell.lexer;

public class TokenizeException extends Exception {
    public TokenizeException() {
    }

    public TokenizeException(String message) {
        super(message);
    }

    public TokenizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenizeException(Throwable cause) {
        super(cause);
    }
}
