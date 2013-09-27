package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class ExpressionStatement extends AbstractStatement {
    private final Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    protected void doExecute(JShellContext context) throws JShellRuntimeException {
        expression.evaluate(context);
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        expression.visit(visitor);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return expression.toString() + ";";
    }
}
