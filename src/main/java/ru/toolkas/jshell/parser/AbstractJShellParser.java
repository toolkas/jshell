package ru.toolkas.jshell.parser;

import ru.toolkas.jshell.lexer.Token;
import ru.toolkas.jshell.lexer.TokenType;

import java.util.List;
import java.util.Stack;

public class AbstractJShellParser {
    private final Stack<Integer> positions = new Stack<Integer>();
    private int position = 0;

    protected void beforeParse() {
        positions.clear();
        position = 0;
    }

    protected boolean check(final List<Token> tokens, TokenType type) {
        Token token = get(tokens, 0);
        if (token.getType() != type) return false;
        position++;
        return true;
    }

    protected boolean check(final List<Token> tokens, TokenType type, String value) {
        Token token = get(tokens, 0);
        if (token.getType() != type) return false;
        if (!token.getValue().equals(value)) return false;
        position++;
        return true;
    }

    protected Token consume(final List<Token> tokens, TokenType type) throws JShellParseException {
        Token token = get(tokens, 0);
        if (token.getType() != type) throw new JShellParseException("expected " + type + ", but " + token);
        return tokens.get(position++);
    }

    protected Token consume(final List<Token> tokens, TokenType type, String value) throws JShellParseException {
        Token token = get(tokens, 0);
        if (token.getType() != type) throw new JShellParseException("expected " + type + ", but " + token);
        if (!token.getValue().equals(value)) {
            throw new JShellParseException("expected value '" + value + "' of type " + type + ", but value is " + token.getValue());
        }
        return tokens.get(position++);
    }

    protected void rollback(int offset) {
        position -= offset;
    }

    protected Token get(final List<Token> tokens, int offset) {
        if (position + offset >= tokens.size()) {
            return new Token(TokenType.EOF, null, -1, -1);
        }

        return tokens.get(position + offset);
    }

    protected Token last(final List<Token> tokens, int offset) {
        return tokens.get(position - offset);
    }

    protected interface Action<T> {
        T execute(final List<Token> tokens) throws JShellParseException;
    }

    protected <T> Result<T> consume(final List<Token> tokens, final Action<T> action) {
        positions.push(position);

        try {
            T value = action.execute(tokens);
            positions.pop();
            return new Result<T>(value, true);
        } catch (JShellParseException ex) {
            position = positions.pop();
            return new Result<T>(null, false);
        }
    }

    protected final class Result<T> {
        private T value;
        private boolean ok;

        private Result(T value, boolean ok) {
            this.value = value;
            this.ok = ok;
        }

        public T value() {
            return value;
        }

        public boolean ok() {
            return ok;
        }
    }
}
