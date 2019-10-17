package com.php25.interpreterlearn.ast;

import com.php25.interpreterlearn.exception.Exceptions;
import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.lexer.TokenType;
import com.php25.interpreterlearn.lexer.Tokens;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/16 09:40
 */
public class ASTParser {


    private List<Token> tokens;

    private int current = 0;

    public ASTParser(List<Token> tokens) {
        this.tokens = tokens;
    }


    /**
     * factor : (PLUS | MINUS) factor | INTEGER | LeftBracket expr RightBracket
     */
    public AST factor() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isPlus(token) || Tokens.isMinus(token)) {
            if (Tokens.isPlus(token)) {
                this.eat(TokenType.PLUS);
            } else {
                this.eat(TokenType.MINUS);
            }
            node = new UnaryOp(token, this.factor());
        } else if (Tokens.isInteger(token)) {
            node = new Digit(token);
            this.eat(TokenType.INTEGER);
        } else if (Tokens.isLeftBracket(token)) {
            this.eat(TokenType.LEFT_BRACKET);
            node = this.expr();
            this.eat(TokenType.RIGHT_BRACKET);
        }
        return node;
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
            node = new BinOp(node, token, this.factor());
            token = getCurrentToken();
        }
        return node;
    }

    /**
     * expr   : term ((PLUS | MINUS) term)*
     * term   : factor ((MUL | DIV | MOD) factor)*
     * factor : (PLUS | MINUS) factor | INTEGER | LeftBracket expr LeftBracket
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
            node = new BinOp(node, token, this.term());
            token = getCurrentToken();
        }
        return node;
    }

    public AST parse() {
        return this.expr();
    }


    private void error() {
        throw Exceptions.throwIllegalStateException("syntax error");
    }

    private void eat(TokenType tokenType) {
        if (getCurrentToken().getType() == tokenType) {
            getNextToken();
        } else {
            throw Exceptions.throwIllegalStateException("syntax error");
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
