package ru.toolkas.jshell.lang.function;

import ru.toolkas.jshell.lang.*;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RequiredArgument(value = {"value", "out"})
public class OpenFile extends AbstractFunction {
    public OpenFile() {
        addArgumentInfo(new ArgumentInfo("value", Type.STRING));
        addArgumentInfo(new ArgumentInfo("out", Type.STRING));
    }

    @Override
    public Map<String, Value> execute(JShellContext context, Map<String, Value> arguments) throws JShellRuntimeException {
        final String value = arguments.get("value").asString(context);
        final String out = arguments.get("out").asString(context);

        return new HashMap<String, Value>() {{
            put(out, new FileValue(new File(value)));
        }};
    }
}
