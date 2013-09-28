package ru.toolkas.jshell.lang;

public interface Function {
    Value execute(Value... args) throws Exception;
}
