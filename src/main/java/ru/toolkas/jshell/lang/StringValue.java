package ru.toolkas.jshell.lang;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;
import ru.toolkas.jshell.runtime.TypeCastException;

import java.io.File;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.STRING;
    }

    @Override
    public boolean asBoolean(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.STRING, Type.BOOLEAN, value);
    }

    @Override
    public String asString(JShellContext context) throws JShellRuntimeException {
        return value;
    }

    @Override
    public double asNumber(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.STRING, Type.NUMBER, value);
    }

    @Override
    public File asFile(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.STRING, Type.FILE, value);
    }

    @Override
    public Object asObject(JShellContext context) throws JShellRuntimeException {
        return asString(context);
    }

    @Override
    public String toString() {
        return '"' + value + '"';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringValue that = (StringValue) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
