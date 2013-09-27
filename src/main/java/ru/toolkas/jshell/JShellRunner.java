package ru.toolkas.jshell;

import ru.toolkas.jshell.lexer.JShellLexer;
import ru.toolkas.jshell.lexer.Token;
import ru.toolkas.jshell.parser.JShellParser_LL;
import ru.toolkas.jshell.runtime.JShellContext;
import ru.toolkas.jshell.runtime.JShellRuntimeException;
import ru.toolkas.jshell.ast.Program;
import ru.toolkas.jshell.ast.VisitNodeException;
import ru.toolkas.jshell.ast.validator.KeyWordValidator;
import ru.toolkas.jshell.parser.JShellParseException;
import ru.toolkas.jshell.lexer.TokenizeException;

import java.io.InputStream;
import java.util.List;

public class JShellRunner {
    public static void main(String[] args) throws TokenizeException, JShellParseException, VisitNodeException, JShellRuntimeException {
        InputStream input = JShellRunner.class.getResourceAsStream("example.txt");

        JShellLexer lexer = new JShellLexer();
        JShellParser_LL parser = new JShellParser_LL();

        List<Token> tokens = lexer.tokens(input);
        Program program = parser.parse(tokens);

        program.visit(new KeyWordValidator());

        JShellContext context = new JShellContext();
        program.run(context);

        System.out.println("context = " + context);
    }
}
