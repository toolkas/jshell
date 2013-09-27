package ru.toolkas.jshell.ast;

import org.apache.commons.lang.ObjectUtils;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.lang.BooleanValue;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class EqualsExpression extends AbstractExpression {
    protected final Expression expr1;
    protected final Expression expr2;

    public EqualsExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        boolean equals = doEquals(context);

        return new BooleanValue(equals);
    }

    protected boolean doEquals(JShellContext context) throws JShellRuntimeException {
        boolean equals = false;

        final Value value1 = expr1.evaluate(context);
        final Value value2 = expr2.evaluate(context);

        if (value1.type() == value2.type()) {
            equals = ObjectUtils.equals(value1.asObject(context), value2.asObject(context));
        }
        return equals;
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
        return expr1 + " == " + expr2;
    }
}
