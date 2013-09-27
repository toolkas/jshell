package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public class IfStatement extends AbstractStatement {
    private final Expression condition;
    private final Statement trueStatement;
    private final Statement falseStatement;

    public IfStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    @Override
    protected void doExecute(JShellContext context) throws JShellRuntimeException {
        if (condition.evaluate(context).asBoolean(context)) {
            trueStatement.execute(context);
        } else {
            if (falseStatement != null) {
                falseStatement.execute(context);
            }
        }
    }

    @Override
    public void visit(Visitor visitor) throws VisitNodeException {
        visitor.start(this);
        condition.visit(visitor);
        trueStatement.visit(visitor);
        if (falseStatement != null) falseStatement.visit(visitor);
        visitor.finish(this);
    }

    @Override
    public String toString() {
        String val = "if(" + condition + ")" + trueStatement;
        if (falseStatement != null) {
            val += " else " + falseStatement;
        }

        return val;
    }
}
