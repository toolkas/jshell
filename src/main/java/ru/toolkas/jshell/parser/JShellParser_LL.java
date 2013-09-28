package ru.toolkas.jshell.parser;

import ru.toolkas.jshell.ast.*;
import ru.toolkas.jshell.lang.NumberValue;
import ru.toolkas.jshell.lang.StringValue;
import ru.toolkas.jshell.lang.Value;
import ru.toolkas.jshell.lexer.Token;
import ru.toolkas.jshell.lexer.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JShellParser_LL extends AbstractJShellParser {
    public Program parse(final List<Token> tokens) throws JShellParseException {
        beforeParse();

        Program program = new Program();

        while (true) {
            if (check(tokens, TokenType.EOF)) {
                break;
            } else {
                Statement statement = statement(tokens);
                program.addStatement(statement);
            }
        }

        return program;
    }

    private Statement statement(final List<Token> tokens) throws JShellParseException {
        Result result;
        if (check(tokens, TokenType.WORD, "if")) {
            Expression condition = parExpression(tokens);

            Statement trueStatement = statement(tokens);
            Statement falseStatement = null;
            if (check(tokens, TokenType.WORD, "else")) {
                falseStatement = statement(tokens);
            }
            return new IfStatement(condition, trueStatement, falseStatement);
        } else if (check(tokens, TokenType.WORD, "while")) {
            Expression condition = parExpression(tokens);
            Statement statement = statement(tokens);

            return new WhileStatement(condition, statement);
        } else if (check(tokens, TokenType.SEPARATOR, ";")) {
            return new EmptyStatement();
        } else if ((result = consume(tokens, new ExpressionAction())).ok()) {
            return new ExpressionStatement((Expression) result.value());
        } else if (check(tokens, TokenType.BRACE, "{")) {
            rollback(1);
            return block(tokens);
        }
        throw new JShellParseException("no statement found at " + get(tokens, 0));
    }

    private Statement block(final List<Token> tokens) throws JShellParseException {
        BlockStatement block = new BlockStatement();
        consume(tokens, TokenType.BRACE, "{");

        Result result;
        while ((result = consume(tokens, new StatementAction())).ok()) {
            block.addStatement((Statement) result.value());
        }

        consume(tokens, TokenType.BRACE, "}");

        return block;
    }

    private Expression functionInvocation(final List<Token> tokens) throws JShellParseException {
        consume(tokens, TokenType.WORD);
        consume(tokens, TokenType.BRACKET, "(");

        String functionName = last(tokens, 2).getValue();

        if (check(tokens, TokenType.BRACKET, ")")) {
            return new FunctionInvocationExpression(functionName);
        }

        final List<Expression> arguments = new ArrayList<Expression>();
        arguments(tokens, arguments);
        consume(tokens, TokenType.BRACKET, ")");

        return new FunctionInvocationExpression(functionName, arguments);
    }

    private void arguments(List<Token> tokens, List<Expression> arguments) throws JShellParseException {
        Result<Expression> result;
        if ((result = consume(tokens, new ArgumentAction())).ok()) {
            Expression expression = result.value();
            arguments.add(expression);
        }

        while (check(tokens, TokenType.SEPARATOR, ",")) {
            Expression expression = expression(tokens);
            arguments.add(expression);
        }
    }

    private Expression expression(final List<Token> tokens) throws JShellParseException {
        if (check(tokens, TokenType.WORD)) {
            if (check(tokens, TokenType.OPERATOR, "=")) {
                String name = last(tokens, 2).getValue();

                Expression expression = expression(tokens);

                return new AssignmentExpression(name, expression);
            } else {
                rollback(1);
            }
        }

        return conditionalExpression(tokens);
    }

    private Expression conditionalExpression(final List<Token> tokens) throws JShellParseException {
        Expression expression = conditionalAndExpression(tokens);

        OrExpression or = null;
        while (true) {
            if (check(tokens, TokenType.OPERATOR, "|")) {
                if (or == null) {
                    or = new OrExpression();
                    or.addExpression(expression);
                }

                or.addExpression(conditionalAndExpression(tokens));
                continue;
            }

            break;
        }

        return or != null ? or : expression;
    }

    private Expression conditionalAndExpression(final List<Token> tokens) throws JShellParseException {
        Expression expression = equalityExpression(tokens);

        AndExpression and = null;
        while (true) {
            if (check(tokens, TokenType.OPERATOR, "&")) {
                if (and == null) {
                    and = new AndExpression();
                    and.addExpression(expression);
                }

                and.addExpression(equalityExpression(tokens));
                continue;
            }

            break;
        }

        return and != null ? and : expression;
    }

    private Expression equalityExpression(final List<Token> tokens) throws JShellParseException {
        Expression expr1 = relationalExpression(tokens);

        if (check(tokens, TokenType.OPERATOR, "==")) {
            return new EqualsExpression(expr1, relationalExpression(tokens));
        }

        if (check(tokens, TokenType.OPERATOR, "!=")) {
            return new NotEqualsExpression(expr1, relationalExpression(tokens));
        }

        return expr1;
    }

    private Expression relationalExpression(final List<Token> tokens) throws JShellParseException {
        Expression expr1 = additiveExpression(tokens);

        if (check(tokens, TokenType.OPERATOR, ">=")) {
            return new BooleanMatchExpression(expr1, BooleanMatchExpression.Operation.MORE_EQ, additiveExpression(tokens));
        }
        if (check(tokens, TokenType.OPERATOR, "<=")) {
            return new BooleanMatchExpression(expr1, BooleanMatchExpression.Operation.LESS_EQ, additiveExpression(tokens));
        }
        if (check(tokens, TokenType.OPERATOR, ">")) {
            return new BooleanMatchExpression(expr1, BooleanMatchExpression.Operation.MORE, additiveExpression(tokens));
        }
        if (check(tokens, TokenType.OPERATOR, "<")) {
            return new BooleanMatchExpression(expr1, BooleanMatchExpression.Operation.LESS, additiveExpression(tokens));
        }

        return expr1;
    }

    private Expression additiveExpression(final List<Token> tokens) throws JShellParseException {
        Expression expression = multiplicativeExpression(tokens);

        while (true) {
            if (check(tokens, TokenType.OPERATOR, "+")) {
                expression = new AddExpression(expression, multiplicativeExpression(tokens));
                continue;
            }

            if (check(tokens, TokenType.OPERATOR, "-")) {
                expression = new MinusExpression(expression, multiplicativeExpression(tokens));
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression multiplicativeExpression(final List<Token> tokens) throws JShellParseException {
        Expression expression = concatenateExpression(tokens);

        while (true) {
            if (check(tokens, TokenType.OPERATOR, "*")) {
                expression = new MultiplyExpression(expression, concatenateExpression(tokens));
                continue;
            }

            if (check(tokens, TokenType.OPERATOR, "/")) {
                expression = new DivideExpression(expression, concatenateExpression(tokens));
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression concatenateExpression(final List<Token> tokens) throws JShellParseException {
        Expression expression = unaryExpression((tokens));

        while (true) {
            if (check(tokens, TokenType.OPERATOR, "^")) {
                expression = new ConcatenateExpression(expression, unaryExpression(tokens));
                continue;
            }

            break;
        }

        return expression;
    }

    private Expression unaryExpression(final List<Token> tokens) throws JShellParseException {
        if (check(tokens, TokenType.OPERATOR, "+")) {
            return primary(tokens);
        } else if (check(tokens, TokenType.OPERATOR, "-")) {
            return new NegativeExpression(primary(tokens));
        } else {
            return unaryExpressionNotPlusMinus(tokens);
        }
    }

    private Expression unaryExpressionNotPlusMinus(final List<Token> tokens) throws JShellParseException {
        if (check(tokens, TokenType.OPERATOR, "!")) {
            return new NotExpression(unaryExpression(tokens));
        } else {
            return primary(tokens);
        }
    }

    private Expression primary(final List<Token> tokens) throws JShellParseException {
        Result<Expression> result;
        if (check(tokens, TokenType.BRACKET, "(")) {
            rollback(1);

            return parExpression(tokens);
        } else if ((result = consume(tokens, new Action<Expression>() {
            @Override
            public Expression execute(List<Token> tokens) throws JShellParseException {
                return functionInvocation(tokens);
            }
        })).ok()) {
            return result.value();
        } else if (check(tokens, TokenType.WORD)) {
            final String identifier = last(tokens, 1).getValue();

            return new Variable(identifier);
        } else {
            Value value = literal(tokens);
            return new ValueExpression(value);
        }
    }

    private Value literal(final List<Token> tokens) throws JShellParseException {
        if (check(tokens, TokenType.STRING)) {
            String value = last(tokens, 1).getValue();

            return new StringValue(value);
        } else {
            consume(tokens, TokenType.NUMBER);

            double value = Double.parseDouble(last(tokens, 1).getValue());
            return new NumberValue(value);
        }
    }

    private Expression parExpression(final List<Token> tokens) throws JShellParseException {
        consume(tokens, TokenType.BRACKET, "(");
        Expression expression = expression(tokens);
        consume(tokens, TokenType.BRACKET, ")");

        return expression;
    }

    private class ExpressionAction implements Action<Expression> {
        @Override
        public Expression execute(List<Token> tokens) throws JShellParseException {
            Expression expression = expression(tokens);
            consume(tokens, TokenType.SEPARATOR, ";");
            return expression;
        }
    }

    private class ArgumentAction implements Action<Expression> {
        @Override
        public Expression execute(List<Token> tokens) throws JShellParseException {
            return expression(tokens);
        }
    }

    private class StatementAction implements Action<Statement> {
        @Override
        public Statement execute(List<Token> tokens) throws JShellParseException {
            return statement(tokens);
        }
    }
}