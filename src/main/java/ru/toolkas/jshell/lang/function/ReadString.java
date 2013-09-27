package ru.toolkas.jshell.lang.function;

import ru.toolkas.jshell.lang.*;
import ru.toolkas.jshell.lang.Type;
import ru.toolkas.jshell.lang.ArgumentInfo;
import ru.toolkas.jshell.lang.Function;
import ru.toolkas.jshell.lang.RequiredArgument;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RequiredArgument("out")
public class ReadString implements Function {
    private static final Map<String, ArgumentInfo> info = new HashMap<String, ArgumentInfo>() {{
        put("out", new ArgumentInfo("out", Type.STRING));
    }};

    @Override
    public ArgumentInfo getArgumentInfo(String name) {
        return info.get(name);
    }

    @Override
    public Map<String, Value> execute(JShellContext context, Map<String, Value> arguments) throws JShellRuntimeException {
        final Scanner scanner = new Scanner(System.in);

        final String value = scanner.nextLine();

        final String out = arguments.get("out").asString(context);

        return new HashMap<String, Value>() {{
            put(out, new StringValue(value));
        }};
    }
}
