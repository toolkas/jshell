package ru.toolkas.jshell.lang;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.io.File;

public interface Value {
    Type type();

    boolean asBoolean(final JShellContext context) throws JShellRuntimeException;

    String asString(final JShellContext context) throws JShellRuntimeException;

    double asNumber(final JShellContext context) throws JShellRuntimeException;

    File asFile(final JShellContext context) throws JShellRuntimeException;

    Object asObject(final JShellContext context) throws JShellRuntimeException;
}
