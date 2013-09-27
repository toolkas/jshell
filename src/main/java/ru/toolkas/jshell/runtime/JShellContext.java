package ru.toolkas.jshell.runtime;

import ru.toolkas.jshell.lang.Function;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.lang.function.OpenFile;
import ru.toolkas.jshell.lang.function.PrintFunction;
import ru.toolkas.jshell.lang.BooleanValue;
import ru.toolkas.jshell.lang.NullValue;
import ru.toolkas.jshell.lang.function.ReadNumber;
import ru.toolkas.jshell.lang.function.ReadString;

import java.util.HashMap;
import java.util.Map;

public class JShellContext {
    private final Map<String, Function> functions = new HashMap<String, Function>();
    private final Map<String, Value> constants = new HashMap<String, Value>();
    private final Map<String, Value> values = new HashMap<String, Value>();

    public JShellContext() {
        constants.put("null", new NullValue());
        constants.put("true", new BooleanValue(true));
        constants.put("false", new BooleanValue(false));

        functions.put("print", new PrintFunction());

        functions.put("readNumber", new ReadNumber());
        functions.put("readString", new ReadString());

        functions.put("openFile", new OpenFile());
    }

    public Value getValue(final String name) {
        if (constants.containsKey(name)) {
            return maskValue(constants.get(name));
        }
        return maskValue(values.get(name));
    }

    public Value setValue(final String name, final Value value) {
        if (!constants.containsKey(name)) {
            values.put(name, value);
        }
        return getValue(name);
    }

    public Function getFunction(final String name) throws JShellRuntimeException {
        Function function = functions.get(name);
        if (function == null) {
            throw new JShellRuntimeException("function " + name + "() is undefined");
        }

        return function;
    }

    public void addFunction(final String name, final Function function) {
        if (function != null) {
            functions.put(name, function);
        }
    }

    @Override
    public String toString() {
        return values.toString();
    }

    public static Value maskValue(Value value) {
        return value != null ? value : new NullValue();
    }
}
