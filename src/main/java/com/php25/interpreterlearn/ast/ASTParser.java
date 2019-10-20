package com.php25.interpreterlearn.ast;

import com.php25.interpreterlearn.ast.node.AssignStatement;
import com.php25.interpreterlearn.ast.node.BinOp;
import com.php25.interpreterlearn.ast.node.CompoundStatement;
import com.php25.interpreterlearn.ast.node.Digit;
import com.php25.interpreterlearn.ast.node.StatementList;
import com.php25.interpreterlearn.ast.node.UnaryOp;
import com.php25.interpreterlearn.ast.node.Variable;
import com.php25.interpreterlearn.exception.Exceptions;
import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.lexer.TokenType;
import com.php25.interpreterlearn.lexer.Tokens;

import java.util.ArrayList;
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
     * variable: identifier
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
     * statement : compound_statement | assignment_statement
     *
     * @return
     */
    public AST statement() {
        AST node = null;
        node = this.compoundStatement();
        if (null != node) {
            return node;
        }
        node = this.assignStatement();
        return node;
    }


    /**
     * compound_statement : { statement_list }
     *
     * @return
     */
    public AST compoundStatement() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isBigLeftBracket(token)) {
            this.eat(TokenType.BIG_LEFT_BRACKET);
            node = this.statementList();
            Token token1 = getCurrentToken();
            this.eat(TokenType.BIG_RIGHT_BRACKET);
            node = new CompoundStatement(token, node, token1);
        }
        return node;
    }


    /**
     * statement_list : statement | statement SEMI statement_list
     */
    public AST statementList() {
        AST node = this.statement();

        List<AST> list = new ArrayList<>();
        StatementList resultNode = new StatementList(list);
        list.add(node);

        Token token = getCurrentToken();
        while (Tokens.isSemicolon(token)) {
            this.eat(TokenType.SEMICOLON);
            node = this.statement();
            if (null == node) {
                //不存在就跳出循环
                break;
            }else {
                //存在，加入statement 并且继续判断
                list.add(node);
                token = getCurrentToken();
            }
        }
        return resultNode;
    }

    /**
     * assign_statement: variable assign expr
     */
    public AST assignStatement() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isIdentifier(token)) {
            node = this.variable();

            token = getCurrentToken();
            if (Tokens.isAssign(token)) {
                this.eat(TokenType.ASSIGN);
                node = new AssignStatement(node, token, this.expr());
            }
        }
        return node;
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
     * statement : compound_statement | assignment_statement | empty
     * compound_statement : { statement_list }
     * statement_list : statement | statement SEMI statement_list
     * assign_statement: variable assign expr
     * variable: identifier
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
        return this.statementList();
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
