package ru.toolkas.jshell.ast;

public interface Node {
    void visit(final Visitor visitor) throws VisitNodeException;
}
