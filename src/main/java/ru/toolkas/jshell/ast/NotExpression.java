package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.lang.BooleanValue;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class NotExpression extends AbstractExpression {
    private final Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        return new BooleanValue(!expression.evaluate(context).asBoolean(context));
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        expression.visit(visitor);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return "!(" + expression + ")";
    }
}
