package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class EmptyStatement extends AbstractStatement {
    @Override
    protected void doExecute(JShellContext context) throws JShellRuntimeException {

    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return ";";
    }
}
