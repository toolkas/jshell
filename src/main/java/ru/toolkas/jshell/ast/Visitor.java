package ru.toolkas.jshell.ast;

public interface Visitor {
    void start(final Node node) throws VisitNodeException;

    void finish(final Node node);
}
