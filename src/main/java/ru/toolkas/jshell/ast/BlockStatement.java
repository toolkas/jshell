package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement extends AbstractStatement {
    private final List<Statement> statements = new ArrayList<Statement>();

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    @Override
    protected void doExecute(JShellContext context) throws JShellRuntimeException {
        for (Statement statement : statements) {
            statement.execute(context);
        }
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        for (Statement statement : statements) {
            statement.visit(visitor);
        }
        visitor.finish(this);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append("{\n");
        for (Statement statement : statements) {
            builder.append("\t").append(statement).append("\n");
        }
        builder.append("}");

        return builder.toString();
    }
}
