package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class AssignmentExpression extends AbstractExpression {
    private final String name;
    private final Expression expression;

    public AssignmentExpression(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        Value value = expression.evaluate(context);
        return context.setValue(name, value);
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        expression.visit(visitor);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return name + " = " + expression.toString();
    }
}
