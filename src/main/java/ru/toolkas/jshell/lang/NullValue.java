package ru.toolkas.jshell.lang;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;
import ru.toolkas.jshell.runtime.TypeCastException;

import java.io.File;

public class NullValue implements Value {
    @Override
    public Type type() {
        return Type.UNDEFINED;
    }

    @Override
    public boolean asBoolean(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.UNDEFINED, Type.BOOLEAN, null);
    }

    @Override
    public String asString(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.UNDEFINED, Type.STRING, null);
    }

    @Override
    public double asNumber(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.UNDEFINED, Type.NUMBER, null);
    }

    @Override
    public File asFile(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.UNDEFINED, Type.FILE, null);
    }

    @Override
    public Object asObject(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.UNDEFINED, Type.NUMBER, null);
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NullValue;
    }
}
