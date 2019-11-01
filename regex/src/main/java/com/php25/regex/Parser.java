package com.php25.regex;

import com.php25.exception.Exceptions;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/18 11:20
 */
class Parser {

    private List<Token> tokens;

    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public AstRegex start() {
        return this.expr();
    }

    public void visitor(AstRegex ast) {
        if (ast instanceof AstClosure) {
            AstClosure astClosure = (AstClosure) ast;
            if (null != astClosure.getExpr()) {
                visitor(astClosure.getExpr());
            }
            System.out.print(astClosure.getClosure().getValue());
        } else if (ast instanceof AstConcat) {
            AstConcat astConcat = (AstConcat) ast;
            if (null != astConcat.getLeftExpr()) {
                visitor(astConcat.getLeftExpr());
            }
            System.out.print(astConcat.getConcat().getValue());

            if (null != astConcat.getRightExpr()) {
                visitor(astConcat.getRightExpr());
            }
        } else if (ast instanceof AstSymbol) {
            AstSymbol astSymbol = (AstSymbol) ast;
            System.out.print(astSymbol.getValue().getValue());
        } else if (ast instanceof AstUnion) {
            AstUnion astUnion = (AstUnion) ast;

            if (null != astUnion.getLeftExpr()) {
                visitor(astUnion.getLeftExpr());
            }
            System.out.print(astUnion.getUnion().getValue());

            if (null != astUnion.getRightExpr()) {
                visitor(astUnion.getRightExpr());
            }
        } else {
            throw Exceptions.throwIllegalStateException("无法识别此AstRegex节点");
        }
    }

    /**
     * symbol: digit | alphabet | others
     *
     * @return
     */
    public AstRegex symbol() {
        Token token = getCurrentToken();
        if (Tokens.isDigit(token)) {
            this.eat(TokenType.DIGIT);
            return new AstSymbol(token);
        } else if (Tokens.isAlphabet(token)) {
            this.eat(TokenType.ALPHABET);
            return new AstSymbol(token);
        } else if (Tokens.isOthers(token)) {
            this.eat(TokenType.OTHERS);
            return new AstSymbol(token);
        } else {
            return null;
        }
    }


    /**
     * expr_bracket: leftBracket expr rightBracket
     *
     * @return
     */
    public AstRegex exprBracket() {
        AstRegex node = null;
        Token token = getCurrentToken();
        if (Tokens.isLeftBracket(token)) {
            this.eat(TokenType.LEFT_BRACKET);
            node = this.expr();
            this.eat(TokenType.RIGHT_BRACKET);
        }
        return node;
    }


    /**
     * closure_expr: (bracket_expr|symbol) (closure)*
     *
     * @return
     */
    public AstRegex closureExpr() {
        AstRegex node = null;
        Token token = getCurrentToken();
        if (Tokens.isLeftBracket(token)) {
            //bracket_expr
            node = this.exprBracket();
        } else if (Tokens.isDigit(token) || Tokens.isAlphabet(token) || Tokens.isOthers(token)) {
            //symbol
            node = this.symbol();
        }

        if (node != null) {
            Token token1 = getCurrentToken();
            if (Tokens.isClosure(token1)) {
                this.eat(TokenType.CLOSURE);
                node = new AstClosure(node, token1);
            }
        }
        return node;
    }

    /**
     * concat_expr: closure_expr (concat concat_expr)*
     *
     * @return
     */
    public AstRegex concatExpr() {
        AstRegex node = this.closureExpr();

        Token token = getCurrentToken();
        while (Tokens.isConcat(token)) {
            this.eat(TokenType.CONCAT);
            node = new AstConcat(node, token, this.concatExpr());
            token = getCurrentToken();
        }
        return node;
    }

    /**
     * union_expr: concat_expr (union union_expr)*
     *
     * @return
     */
    public AstRegex unionExpr() {
        AstRegex node = this.concatExpr();
        Token token = getCurrentToken();
        while (Tokens.isUnion(token)) {
            this.eat(TokenType.UNION);
            node = new AstUnion(node, token, this.unionExpr());
            token = getCurrentToken();
        }
        return node;
    }

    /**
     * *>.>|
     * <p>
     * symbol: digit | alphabet | others
     * expr_bracket: leftBracket expr rightBracket
     * closure_expr: (bracket_expr|symbol) (closure)*
     * concat_expr: closure_expr (concat concat_expr)*
     * union_expr: concat_expr (union union_expr)*
     * expr: union_expr
     *
     * @return
     */
    public AstRegex expr() {
        return this.unionExpr();
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

    private Token getCurrentToken() {
        return tokens.get(current);
    }

    private Token getNextToken() {
        if ((this.current + 1) < tokens.size()) {
            return this.tokens.get(++current);
        } else {
            return null;
        }
    }
}
