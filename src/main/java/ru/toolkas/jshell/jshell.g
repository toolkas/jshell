program: statement*

statement
    :   block
    |   'if' parExpression statement ('else' statement)?
    |   'while' parExpression statement
    |   expression  ';'
    |   ';'
    ;

block
    :   '{' (statement)* '}'
    ;

functionInvocation
    :   IDENTIFIER '(' (arguments)? ')'
    ;

arguments
    :   expression (',' expression)*
    ;

parExpression
    :   '(' expression ')'
    ;

expression
    :   assignmentExpression | conditionalExpression
    ;

assignmentExpression
    :   IDENTIFIER '=' expression
    ;

conditionalExpression
    :   conditionalAndExpression ('|' conditionalAndExpression)*
    ;

conditionalAndExpression
    :   equalityExpression ('&' equalityExpression)*
    ;

equalityExpression
    :   relationalExpression (('==' | '!=') relationalExpression)?
    ;

relationalExpression
    :   additiveExpression (relationalOp additiveExpression)?
    ;

relationalOp
    :    '<='
    |    '>='
    |   '<'
    |   '>'
    ;

additiveExpression
    :   multiplicativeExpression
        (
            (   '+'
            |   '-'
            )
            multiplicativeExpression
         )*
    ;

multiplicativeExpression
    :
        concatenateExpression
        (
            ('*' | '/') concatenateExpression
        )*
    ;

concatenateExpression
    :   unaryExpression ('^' unaryExpression)*
    ;

unaryExpression
    :   '+'  primary
    |   '-' primary
    |   unaryExpressionNotPlusMinus
    ;

unaryExpressionNotPlusMinus
    :   '!' unaryExpression
    |   primary
    ;

primary
    :   parExpression
    |   IDENTIFIER
    |   literal
    |   functionInvocation
    ;

literal
    |   DOUBLELITERAL
    |   STRINGLITERAL
    |   TRUE
    |   FALSE
    |   NULL
    ;