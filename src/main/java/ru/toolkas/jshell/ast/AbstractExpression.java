package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public abstract class AbstractExpression implements Expression {
    @Override
    public Value evaluate(JShellContext context) throws JShellRuntimeException {
        try {
            return doEvaluate(context);
        } catch (Exception ex) {
            throw new JShellRuntimeException("'" + this + "' evaluation failed", ex);
        }
    }

    protected abstract Value doEvaluate(final JShellContext context) throws Exception;
}
