package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.ArgumentInfo;
import ru.toolkas.jshell.lang.Function;
import ru.toolkas.jshell.lang.RequiredArgument;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FunctionStatement extends AbstractStatement {
    private final Map<String, Expression> arguments = new HashMap<String, Expression>();
    private final String name;

    public FunctionStatement(String name) {
        this.name = name;
    }

    public FunctionStatement(String name, final Map<String, Expression> arguments) {
        this.name = name;

        if (arguments != null) this.arguments.putAll(arguments);
    }

    @Override
    protected void doExecute(JShellContext context) throws JShellRuntimeException {
        Function function = context.getFunction(name);

        final Map<String, Value> arguments = new HashMap<String, Value>();
        for (Map.Entry<String, Expression> entry : this.arguments.entrySet()) {
            Value value = entry.getValue().evaluate(context);

            arguments.put(entry.getKey(), value);
        }

        RequiredArgument required = function.getClass().getAnnotation(RequiredArgument.class);
        if (required != null) {
            for (String name : required.value()) {
                if (!arguments.containsKey(name)) {
                    throw new JShellRuntimeException("there is no required argument [" + name + "] in " + this);
                }

                ArgumentInfo info = function.getArgumentInfo(name);
                if (info != null) {
                    Value value = arguments.get(name);
                    if (info.getType() != value.type()) {
                        throw new JShellRuntimeException("argument [" + name + "] must be instance of " + info.getType() + ", now " + value.type());
                    }
                }
            }
        }

        final Map<String, Value> out = function.execute(context, arguments);
        if (out != null) {
            for (Map.Entry<String, Value> entry : out.entrySet()) {
                context.setValue(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(name);
        builder.append("(");
        for (Iterator<Map.Entry<String, Expression>> iterator = arguments.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Expression> entry = iterator.next();
            builder.append(entry.getKey()).append(": ").append(entry.getValue());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append(");");

        return builder.toString();
    }
}
