package ru.toolkas.jshell.lang.function;

import ru.toolkas.jshell.lang.RequiredArgument;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.Map;

@RequiredArgument("value")
public class PrintFunction extends AbstractFunction {
    @Override
    public Map<String, Value> execute(JShellContext context, Map<String, Value> arguments) throws JShellRuntimeException {
        System.out.println(arguments.get("value").asString(context));
        return null;
    }
}
