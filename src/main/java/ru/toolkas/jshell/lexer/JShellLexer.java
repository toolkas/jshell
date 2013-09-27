package ru.toolkas.jshell.lexer;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JShellLexer {
    private static final Set<Integer> operators = new HashSet<Integer>() {{
        //relation operators
        add((int) '>');
        add((int) '<');
        add((int) '=');

        //logical operators
        add((int) '!');
        add((int) '&');
        add((int) '|');

        //math operators
        add((int) '+');
        add((int) '-');
        add((int) '*');
        add((int) '/');

        //string operators
        add((int) '^');
    }};

    private static final Set<Integer> separators = new HashSet<Integer>() {{
        add((int) ';');
        add((int) ':');
        add((int) ',');
    }};

    private TokenizeState state = TokenizeState.DEFAULT;
    private String value = "";

    private int column = 1;
    private int offset = 0;

    public List<Token> tokens(final InputStream input) throws TokenizeException {
        try {
            final List<Token> tokens = new ArrayList<Token>();
            final Buffer buffer = new Buffer(input);

            int b;
            while ((b = buffer.read()) != -1) {
                Token token = process(buffer, b);
                if (token != null) {
                    tokens.add(token);
                }
            }

            if (state != TokenizeState.DEFAULT) {
                Token token = process(buffer, b);
                if (token != null) {
                    tokens.add(token);
                }
            }

            if (StringUtils.isNotBlank(value)) {
                throw new TokenizeException("can't parse '" + value + "'");
            }

            return tokens;
        } catch (Exception ex) {
            if (ex instanceof TokenizeException) {
                throw (TokenizeException) ex;
            }
            throw new TokenizeException(ex);
        }
    }

    private Token process(Buffer buffer, int b) throws IOException, TokenizeException {
        char c = (char) b;

        switch (state) {
            case DEFAULT:
                if (StringUtils.isNotBlank(value)) {
                    throw new TokenizeException("value '" + value + "' must be empty");
                }

                if (Character.isJavaIdentifierStart(b)) {
                    state = TokenizeState.IDENTIFIER;
                    value += c;
                } else if (Character.isDigit(b)) {
                    state = TokenizeState.NUMBER_START;
                    buffer.push(b);
                } else if (b == '"') {
                    state = TokenizeState.STRING;
                } else if (Character.isWhitespace(b)) {
                    return null;
                } else if (isOperator(b)) {
                    state = TokenizeState.OPERATOR;
                    value += c;
                } else if (isSeparator(b)) {
                    return new Token(TokenType.SEPARATOR, String.valueOf(c), column, offset);
                } else if (c == '{' || c == '}') {
                    return new Token(TokenType.BRACE, String.valueOf(c), column, offset);
                } else if (c == '(' || c == ')') {
                    return new Token(TokenType.BRACKET, String.valueOf(c), column, offset);
                } else {
                    throw new TokenizeException("unexpected character '" + c + "' in state " + state);
                }
                break;
            case IDENTIFIER:
                if (Character.isJavaIdentifierPart(b)) {
                    value += c;
                } else {
                    return createToken(buffer, b, TokenType.WORD, true);
                }
                break;
            case NUMBER_START:
                if (Character.isDigit(b)) {
                    value += c;
                } else if (b == '.') {
                    value += c;
                    state = TokenizeState.NUMBER_END;
                } else {
                    return createToken(buffer, b, TokenType.NUMBER, true);
                }
                break;
            case NUMBER_END:
                if (Character.isDigit(b)) {
                    value += c;
                } else {
                    return createToken(buffer, b, TokenType.NUMBER, true);
                }
                break;
            case STRING:
                if (c == '"') {
                    return createToken(buffer, b, TokenType.STRING, false);
                } else if (isLineFeed(b)) {
                    throw new TokenizeException("unexpected character '" + c + "' in state " + state);
                } else {
                    value += c;
                }
                break;
            case OPERATOR:
                //Ищем !=, ==, >=, <=
                if ((value.equals("!") || value.equals("=") || value.equals(">") || value.equals("<")) && c == '=') {
                    value += c;
                } else {
                    return createToken(buffer, b, TokenType.OPERATOR, true);
                }
        }

        return null;
    }

    private Token createToken(Buffer buffer, int b, TokenType type, boolean push) {
        Token token = new Token(type, value, column, offset);
        value = "";

        state = TokenizeState.DEFAULT;
        if (push) {
            buffer.push(b);
        }
        return token;
    }

    public static boolean isLineFeed(int b) {
        return b == '\n' || b == '\r';
    }

    public static boolean isSeparator(int b) {
        return separators.contains(b);
    }

    public static boolean isOperator(int b) {
        return operators.contains(b);
    }

    private class Buffer {
        private final Stack<Integer> stack = new Stack<Integer>();
        private final InputStream input;

        private Buffer(InputStream input) {
            this.input = input;
        }

        public int read() throws IOException {
            return stack.isEmpty() ? input.read() : stack.pop();
        }

        public void push(int b) {
            stack.push(b);
        }
    }

    private static enum TokenizeState {
        DEFAULT, IDENTIFIER, NUMBER_START, NUMBER_END, STRING, WS, OPERATOR
    }
}
