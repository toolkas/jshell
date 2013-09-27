package ru.toolkas.jshell.ast.validator;

import ru.toolkas.jshell.ast.Node;
import ru.toolkas.jshell.ast.Variable;
import ru.toolkas.jshell.ast.VisitNodeException;
import ru.toolkas.jshell.ast.Visitor;

import java.util.HashSet;
import java.util.Set;

public class KeyWordValidator implements Visitor {
    private static final Set<String> keywords = new HashSet<String>();

    static {
        keywords.add("if");
        keywords.add("while");
    }

    @Override
    public void start(Node node) throws VisitNodeException {
        if (node instanceof Variable) {
            Variable variable = (Variable) node;
            if(keywords.contains(variable.getName())) {
                throw new VisitNodeException("variable's name can't be one of keywords: " + keywords);
            }
        }
    }

    @Override
    public void finish(Node node) {

    }
}
