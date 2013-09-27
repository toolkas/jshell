package ru.toolkas.jshell.runtime;

import ru.toolkas.jshell.lang.Type;

public class TypeCastException extends JShellRuntimeException {
    public TypeCastException(Type valueType, Type castType, Object value) {
        super("can't cast value [" + value + "] with type '" + valueType + "' to '" + castType + "'");
    }
}
