package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.lang.BooleanValue;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrExpression extends AbstractExpression {
    private List<Expression> expressions = new ArrayList<Expression>();

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        boolean ok = false;

        for (Expression expression : expressions) {
            if (expression.evaluate(context).asBoolean(context)) {
                ok = true;
                break;
            }
        }
        return new BooleanValue(ok);
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        for (Expression expression : expressions) {
            expression.visit(visitor);
        }
        visitor.finish(this);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (Iterator<Expression> iterator = expressions.iterator(); iterator.hasNext();) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(" | ");
            }
        }

        return builder.toString();
    }
}
