package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class WhileStatement extends AbstractStatement {
    private final Expression condition;
    private final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    protected void doExecute(JShellContext context) throws JShellRuntimeException {
        while (condition.evaluate(context).asBoolean(context)) {
            statement.execute(context);
        }
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        condition.visit(visitor);
        statement.visit(visitor);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        return "while(" + condition + ")" + statement;
    }
}
