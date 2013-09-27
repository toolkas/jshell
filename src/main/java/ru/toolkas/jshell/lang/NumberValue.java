package ru.toolkas.jshell.lang;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;
import ru.toolkas.jshell.runtime.TypeCastException;

import java.io.File;

public class NumberValue implements Value {
    private final double value;

    public NumberValue(double value) {
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.NUMBER;
    }

    @Override
    public boolean asBoolean(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.NUMBER, Type.BOOLEAN, value);
    }

    @Override
    public String asString(JShellContext context) throws JShellRuntimeException {
        return String.valueOf(value);
    }

    @Override
    public double asNumber(JShellContext context) throws JShellRuntimeException {
        return value;
    }

    @Override
    public File asFile(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.NUMBER, Type.FILE, value);
    }

    @Override
    public Object asObject(JShellContext context) throws JShellRuntimeException {
        return asNumber(context);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberValue that = (NumberValue) o;

        if (Double.compare(that.value, value) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = value != +0.0d ? Double.doubleToLongBits(value) : 0L;
        return (int) (temp ^ (temp >>> 32));
    }
}
