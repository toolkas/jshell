package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class Variable extends AbstractExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    protected Value doEvaluate(JShellContext context) throws JShellRuntimeException {
        return context.getValue(name);
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
