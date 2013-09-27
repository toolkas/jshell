package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.lang.BooleanValue;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class BooleanMatchExpression extends AbstractExpression {
    private final Expression expr1;
    private final Operation operation;
    private final Expression expr2;

    public BooleanMatchExpression(Expression expr1, Operation operation, Expression expr2) {
        this.expr1 = expr1;
        this.operation = operation;
        this.expr2 = expr2;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        boolean ok = false;

        switch (operation) {
            case LESS_EQ:
                ok = expr1.evaluate(context).asNumber(context) <= expr2.evaluate(context).asNumber(context);
                break;
            case MORE_EQ:
                ok = expr1.evaluate(context).asNumber(context) >= expr2.evaluate(context).asNumber(context);
                break;
            case LESS:
                ok = expr1.evaluate(context).asNumber(context) < expr2.evaluate(context).asNumber(context);
                break;
            case MORE:
                ok = expr1.evaluate(context).asNumber(context) > expr2.evaluate(context).asNumber(context);
                break;
        }
        return new BooleanValue(ok);
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        expr1.visit(visitor);
        operation.visit(visitor);
        expr2.visit(visitor);
        visitor.finish(this);
    }

    public static enum Operation implements Node {
        LESS_EQ("<="), MORE_EQ(">="), LESS("<"), MORE(">");

        private String value;

        private Operation(String value) {
            this.value = value;
        }

        @Override
        public void visit(Visitor visitor) throws VisitNodeException {
            visitor.start(this);
            visitor.finish(this);
        }
    }

    @Override
    public String toString() {
        return expr1 + " " + operation.value + " " + expr2;
    }
}
