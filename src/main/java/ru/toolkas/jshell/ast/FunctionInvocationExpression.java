package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Function;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class FunctionInvocationExpression extends AbstractExpression {
    private final List<Expression> arguments = new ArrayList<Expression>();
    private final String name;

    public FunctionInvocationExpression(String name) {
        this.name = name;
    }

    public FunctionInvocationExpression(String name, List<Expression> arguments) {
        this.name = name;
        if (arguments != null) {
            this.arguments.addAll(arguments);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws Exception {
        Function function = context.getFunction(name);

        if (function == null) {
            throw new JShellRuntimeException("Function [" + name + "] is not found");
        }

        final List<Value> values = new ArrayList<Value>();
        for (Expression expression : arguments) {
            Value value = expression.evaluate(context);
            values.add(value);
        }

        return function.execute(values.toArray(new Value[values.size()]));
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        for (Expression expression : arguments) {
            expression.visit(visitor);
        }
        visitor.finish(this);
    }
}
