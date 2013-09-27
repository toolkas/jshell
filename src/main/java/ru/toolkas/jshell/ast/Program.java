package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class Program implements Node {
    private final List<Statement> statements = new ArrayList<Statement>();

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public void run(final JShellContext context) throws JShellRuntimeException {
        for (Statement statement : statements) {
            statement.execute(context);
        }
    }

    public void visit(final Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        for (Statement statement : statements) {
            statement.visit(visitor);
        }
        visitor.finish(this);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (Statement statement : statements) {
            builder.append(statement).append("\n");
        }

        return builder.toString();
    }
}
