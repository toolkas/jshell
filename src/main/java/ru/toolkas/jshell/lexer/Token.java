package ru.toolkas.jshell.lexer;

public class Token {
    private TokenType type;
    private String value;

    private int column;
    private int offset;

    public Token(TokenType type, String value, int column, int offset) {
        this.type = type;
        this.value = value;
        this.column = column;
        this.offset = offset;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getColumn() {
        return column;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "Token{" + type + ",'" + value + "',(" + column + ", " + offset + ")}";
    }
}
