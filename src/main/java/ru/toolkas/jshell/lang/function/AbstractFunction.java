package ru.toolkas.jshell.lang.function;

import ru.toolkas.jshell.lang.ArgumentInfo;
import ru.toolkas.jshell.lang.Function;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFunction implements Function {
    private final Map<String, ArgumentInfo> info = new HashMap<String, ArgumentInfo>();

    @Override
    public ArgumentInfo getArgumentInfo(String name) {
        return info.get(name);
    }

    protected void addArgumentInfo(ArgumentInfo info) {
        if (info != null) {
            this.info.put(info.getName(), info);
        }
    }
}
