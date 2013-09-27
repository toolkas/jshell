package ru.toolkas.jshell.lang;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;
import ru.toolkas.jshell.runtime.TypeCastException;

import java.io.File;

public class FileValue implements Value {
    private File value;

    public FileValue(File value) {
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.FILE;
    }

    @Override
    public boolean asBoolean(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.FILE, Type.BOOLEAN, value);
    }

    @Override
    public String asString(JShellContext context) throws JShellRuntimeException {
        return value.getAbsolutePath();
    }

    @Override
    public double asNumber(JShellContext context) throws JShellRuntimeException {
        throw new TypeCastException(Type.FILE, Type.NUMBER, value);
    }

    @Override
    public File asFile(JShellContext context) throws JShellRuntimeException {
        return value;
    }

    @Override
    public Object asObject(JShellContext context) throws JShellRuntimeException {
        return asString(context);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public File getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileValue fileValue = (FileValue) o;

        if (value != null ? !value.equals(fileValue.value) : fileValue.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
