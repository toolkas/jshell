package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.*;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class ConcatenateExpression extends AbstractExpression {
    private final Expression expr1;
    private final Expression expr2;

    public ConcatenateExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        return new StringValue(expr1.evaluate(context).asString(context) + expr2.evaluate(context).asString(context));
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        expr1.visit(visitor);
        expr2.visit(visitor);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return expr1 + " ^ " + expr2;
    }
}
