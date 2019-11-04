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
     * assign_statement->variable assign expr
     */
    public AST assignStatement() {
        AST node = null;
        node = this.variableDeclare();
        if (null != node) {
            Token token = getCurrentToken();
            if (Tokens.isAssign(token)) {
                this.eat(TokenType.ASSIGN);
                node = new AssignStatement(node, token, this.expr());
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
     * statement_list->(assign_statement SEMI)+
     */
    public AST statementList() {
        AST node = this.assignStatement();

        List<AST> list = new ArrayList<>();
        StatementList resultNode = new StatementList(list);
        list.add(node);

        Token token = getCurrentToken();
        while (Tokens.isSemicolon(token)) {
            this.eat(TokenType.SEMICOLON);
            node = this.assignStatement();
            if (null == node) {
                //不存在就跳出循环
                break;
            } else {
                //存在，加入statement 并且继续判断
                list.add(node);
                token = getCurrentToken();
            }
        }
        return resultNode;
    }


    /**
     * term : factor ((MUL | DIV | MOD) factor)*
     */
    public AST term() {
        AST node = this.factor();
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
            node = new Term(node, token, this.factor());
            token = getCurrentToken();
        }

        return node;
    }

    /**
     * unary_factor-> (PLUS | MINUS) factor
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
            node = new UnaryFactor(token, this.factor());
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
        return this.program();
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
