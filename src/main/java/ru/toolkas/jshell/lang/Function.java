package ru.toolkas.jshell.lang;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.Map;

public interface Function {
    ArgumentInfo getArgumentInfo(final String name);

    Map<String, Value> execute(final JShellContext context, final Map<String, Value> arguments) throws JShellRuntimeException;
}
