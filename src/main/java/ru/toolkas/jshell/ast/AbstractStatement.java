package ru.toolkas.jshell.ast;

import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;

public abstract class AbstractStatement implements Statement {
    @Override
    public void execute(JShellContext context) throws JShellRuntimeException {
        try {
            doExecute(context);
        } catch (Exception ex) {
            throw new JShellRuntimeException("execute statement '" + this + "' failed", ex);
        }
    }

    protected abstract void doExecute(final JShellContext context) throws Exception;
}
