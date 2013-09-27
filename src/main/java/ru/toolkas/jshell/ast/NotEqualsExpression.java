package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class NotEqualsExpression extends EqualsExpression {
    public NotEqualsExpression(Expression expr1, Expression expr2) {
        super(expr1, expr2);
    }

    @Override
    protected boolean doEquals(JShellContext context) throws JShellRuntimeException {
        return !super.doEquals(context);
    }

    @Override
    public String toString() {
        return expr1 + " != " + expr2;
    }
}
