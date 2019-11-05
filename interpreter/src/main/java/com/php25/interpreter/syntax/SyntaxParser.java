package com.php25.interpreter.syntax;

import com.php25.exception.Exceptions;
import com.php25.interpreter.AST;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.lexer.TokenType;
import com.php25.interpreter.lexer.Tokens;
import com.php25.interpreter.syntax.node.AssignStatement;
import com.php25.interpreter.syntax.node.Expr;
import com.php25.interpreter.syntax.node.Factor;
import com.php25.interpreter.syntax.node.Factor0;
import com.php25.interpreter.syntax.node.FunctionBody;
import com.php25.interpreter.syntax.node.FunctionDeclare;
import com.php25.interpreter.syntax.node.FunctionName;
import com.php25.interpreter.syntax.node.FunctionParams;
import com.php25.interpreter.syntax.node.Program;
import com.php25.interpreter.syntax.node.StatementList;
import com.php25.interpreter.syntax.node.Term;
import com.php25.interpreter.syntax.node.UnaryFactor;
import com.php25.interpreter.syntax.node.Variable;
import com.php25.interpreter.syntax.node.VariableDeclare;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/16 09:40
 */
public class SyntaxParser {

    private List<Token> tokens;

    private int current = 0;

    public SyntaxParser(List<Token> tokens) {
        this.tokens = tokens;
    }


    /**
     * assign_statement->(variable|variable_declare) assign expr
     */
    public AST assignStatement() {
        AST node = null;
        node = this.variableDeclare();
        if (null != node) {
            Token token = getCurrentToken();
            if (Tokens.isAssign(token)) {
                this.eat(TokenType.ASSIGN);
                node = new AssignStatement(node, token, this.expr());
                return node;
            }
        }

        node = this.variable();
        if (null != node) {
            Token token = getCurrentToken();
            if (Tokens.isAssign(token)) {
                this.eat(TokenType.ASSIGN);
                node = new AssignStatement(node, token, this.expr());
                return node;
            }
        }

        return node;
    }


    /**
     * expr->term ((PLUS | MINUS) term)*
     */
    public AST expr() {
        AST node = term();
        Token token = getCurrentToken();

        //注意这里是用while 应为((PLUS | MINUS) term)*代表没有或者多个
        while (Tokens.isPlus(token)
                || Tokens.isMinus(token)
        ) {
            if (Tokens.isPlus(token)) {
                this.eat(TokenType.PLUS);
            } else {
                this.eat(TokenType.MINUS);
            }
            node = new Expr(node, token, this.term());
            token = getCurrentToken();
        }
        return node;
    }

    /**
     * factor -> factor0 | variable | LeftParenthesis expr RightParenthesis
     */
    public AST factor() {
        AST node = null;
        Token token = getCurrentToken();

        node = this.factor0();
        if (node != null) {
            //factor0
            return new Factor(node);
        }

        node = this.variable();
        if (node != null) {
            //variable
            return new Factor(node);
        }

        if (Tokens.isLeftBracket(token)) {
            this.eat(TokenType.LEFT_BRACKET);
            node = this.expr();
            this.eat(TokenType.RIGHT_BRACKET);
            node = new Factor(node);
        }
        return node;
    }

    /**
     * factor0  -> integer|string
     *
     * @return
     */
    public AST factor0() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isInteger(token)) {
            this.eat(TokenType.INTEGER);
            node = new Factor0(token);
        } else if (Tokens.isString(token)) {
            this.eat(TokenType.STRING);
            node = new Factor0(token);
        }
        return node;
    }

    /**
     * function_body -> {statement_list (return variable)?}
     *
     * @return
     */
    public AST functionBody() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isBigLeftBracket(token)) {
            this.eat(TokenType.BIG_LEFT_BRACKET);
            node = this.statementList();
            Token token1 = getCurrentToken();
            if (Tokens.isReturn(token1)) {
                this.eat(TokenType.RETURN);
                AST returnValue = this.variable();
                this.eat(TokenType.BIG_RIGHT_BRACKET);
                node = new FunctionBody(node, returnValue);
                return node;
            } else {
                this.eat(TokenType.BIG_RIGHT_BRACKET);
                node = new FunctionBody(node, null);
                return node;
            }
        }
        return node;
    }

    /**
     * function_declare-> function_name function_params function_body
     *
     * @return
     */
    public AST functionDeclare() {
        AST functionName = this.functionName();
        AST functionParams = this.functionParams();
        AST functionBody = this.functionBody();
        if (functionName != null && functionParams != null && functionBody != null) {
            return new FunctionDeclare(functionName, functionParams, functionBody);
        } else {
            return null;
        }
    }

    /**
     * function_name -> function variable
     *
     * @return
     */
    public AST functionName() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isFunction(token)) {
            this.eat(TokenType.FUNCTION);
            node = new FunctionName(this.variable());
        }
        return node;
    }

    /**
     * function_params -> LeftParenthesis variable? (,variable)* RightParenthesis
     *
     * @return
     */
    public AST functionParams() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isLeftBracket(token)) {
            this.eat(TokenType.LEFT_BRACKET);
            node = this.variable();
            if (null != node) {
                List<AST> variables = new ArrayList<>();
                variables.add(node);
                token = getCurrentToken();
                while (Tokens.isComma(token)) {
                    this.eat(TokenType.COMMA);
                    node = this.variable();
                    if (null != node) {
                        variables.add(node);
                    } else {
                        error();
                    }
                    token = getCurrentToken();
                }
                this.eat(TokenType.RIGHT_BRACKET);
                node = new FunctionParams(variables);
            } else {
                this.eat(TokenType.RIGHT_BRACKET);
                node = new FunctionParams(null);
            }
        }
        return node;
    }

    /**
     * program  -> statement_list
     *
     * @return
     */
    public AST program() {
        AST node = this.statementList();
        if (null != node) {
            node = new Program(node);
        }
        return node;
    }

    /**
     * statement_list->((assign_statement|function_invoke|function_declare) SEMI)+
     */
    public AST statementList() {
        AST node = null;

        //尝试assign_statement
        node = this.assignStatement();

        if (node == null) {
            //尝试function_declare
            node = this.functionDeclare();
        }

        if (null != node) {
            //存在，加入statement 并且继续判断
            List<AST> list = new ArrayList<>();
            StatementList resultNode = new StatementList(list);
            list.add(node);

            Token token = getCurrentToken();
            while (Tokens.isSemicolon(token)) {
                this.eat(TokenType.SEMICOLON);

                //尝试assign_statement
                node = this.assignStatement();
                if (null != node) {
                    //存在，加入statement 并且继续判断
                    list.add(node);
                    token = getCurrentToken();
                    continue;
                }

                //尝试function_declare
                node = this.functionDeclare();
                if (null != node) {
                    //存在，加入statement 并且继续判断
                    list.add(node);
                    token = getCurrentToken();
                    continue;
                }

                if (node == null) {
                    break;
                }
            }
            return resultNode;
        }
        return node;
    }


    /**
     * term : unary_factor ((MUL | DIV | MOD) unary_factor)*
     */
    public AST term() {
        AST node = this.unaryFactor();
        Token token = getCurrentToken();

        //注意这里是用while 应为((MUL | DIV) factor)*表没有或者多个
        while (Tokens.isMul(token)
                || Tokens.isDiv(token)
                || Tokens.isMod(token)
        ) {
            if (Tokens.isMul(token)) {
                this.eat(TokenType.MUL);
            } else if (Tokens.isMod(token)) {
                this.eat(TokenType.MOD);
            } else {
                this.eat(TokenType.DIV);
            }
            node = new Term(node, token, this.unaryFactor());
            token = getCurrentToken();
        }
        return node;
    }

    /**
     * unary_factor -> ((PLUS | MINUS) unary_factor) | factor
     *
     * @return
     */
    public AST unaryFactor() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isPlus(token) || Tokens.isMinus(token)) {
            if (Tokens.isPlus(token)) {
                this.eat(TokenType.PLUS);
            } else {
                this.eat(TokenType.MINUS);
            }

            node = this.unaryFactor();
            if (null != node) {
                node = new UnaryFactor(token, node);
                return node;
            }
        } else {
            node = this.factor();
            if (null != node) {
                return node;
            }
        }
        return node;
    }


    /**
     * variable -> identifier
     *
     * @return
     */
    public AST variable() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isIdentifier(token)) {
            this.eat(TokenType.IDENTIFIER);
            node = new Variable(token);
        }
        return node;
    }


    /**
     * variable_declare -> (var|let) identifier(,identifier)*
     */
    public AST variableDeclare() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isLet(token) || Tokens.isVar(token)) {
            if (Tokens.isLet(token)) {
                this.eat(TokenType.LET);
            } else {
                this.eat(TokenType.VAR);
            }
            List<Token> identifiers = new ArrayList<>();
            Token identifier = getCurrentToken();

            if (Tokens.isIdentifier(identifier)) {
                this.eat(TokenType.IDENTIFIER);
                identifiers.add(identifier);
                node = new VariableDeclare(token, identifiers);
            }

            Token currentToken = getCurrentToken();
            while (Tokens.isComma(currentToken)) {
                this.eat(TokenType.COMMA);
                Token tmp = getCurrentToken();
                this.eat(TokenType.IDENTIFIER);
                identifiers.add(tmp);
                currentToken = getCurrentToken();
            }
        }
        return node;
    }

    public AST parse() {
        AST ast = this.program();
        Token token = getCurrentToken();
        if (!Tokens.isEOF(token)) {
            error();
        }
        return ast;
    }


    private void error() {
        throw Exceptions.throwIllegalStateException("syntax error");
    }

    private void eat(TokenType tokenType) {
        if (getCurrentToken().getType() == tokenType) {
            getNextToken();
        } else {
            error();
        }
    }


    private Token getNextToken() {
        if ((current + 1) < tokens.size()) {
            return tokens.get(++current);
        } else {
            return null;
        }
    }

    private Token getCurrentToken() {
        Token token = tokens.get(current);
        return token;
    }
}
