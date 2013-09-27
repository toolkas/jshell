package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public interface Expression extends Node {
    Value evaluate(final JShellContext context) throws JShellRuntimeException;
}
