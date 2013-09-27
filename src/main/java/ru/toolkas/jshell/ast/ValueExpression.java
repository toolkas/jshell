package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class ValueExpression extends AbstractExpression {
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        return value;
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
