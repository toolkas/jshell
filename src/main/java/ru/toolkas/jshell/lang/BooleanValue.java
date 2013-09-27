package ru.toolkas.jshell.lang;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;
import ru.toolkas.jshell.runtime.TypeCastException;

import java.io.File;

public class BooleanValue implements Value {
    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.BOOLEAN;
    }

    @Override
    public boolean asBoolean(JShellContext context) throws JShellRuntimeException {
        return value;
    }

    @Override
    public String asString(JShellContext context) throws JShellRuntimeException {
        return String.valueOf(value);
    }

    @Override
    public double asNumber(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.BOOLEAN, Type.NUMBER, value);
    }

    @Override
    public File asFile(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.BOOLEAN, Type.FILE, value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Object asObject(JShellContext context) throws JShellRuntimeException {
        return asBoolean(context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanValue that = (BooleanValue) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }
}
