package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.lang.NumberValue;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class NegativeExpression extends AbstractExpression {
    private final Expression expression;

    public NegativeExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        return new NumberValue(-expression.evaluate(context).asNumber(context));
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        expression.visit(visitor);
        visitor.finish(this);
    }
}
