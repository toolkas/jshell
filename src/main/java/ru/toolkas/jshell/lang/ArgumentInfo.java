package ru.toolkas.jshell.lang;

public final class ArgumentInfo {
    private String name;
    private Type type;

    public ArgumentInfo(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
