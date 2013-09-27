package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public interface Statement extends Node {
    void execute(final JShellContext context) throws JShellRuntimeException;

    void visit(final Visitor visitor) throws VisitNodeException;
}
