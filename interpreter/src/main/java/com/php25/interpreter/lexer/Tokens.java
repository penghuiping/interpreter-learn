package com.php25.interpreter.lexer;

/**
 * @author penghuiping
 * @date 2019/10/14 18:09
 */
public class Tokens {

    public static boolean isLeftBlockComment(Token token) {
        return null != token && (token.getType() == TokenType.LEFT_BLOCK_COMMENT);
    }

    public static boolean isRightBlockComment(Token token) {
        return null != token && (token.getType() == TokenType.RIGHT_BLOCK_COMMENT);
    }

    public static boolean isOneLineComment(Token token) {
        return null != token && (token.getType() == TokenType.ONE_LINE_COMMENT);
    }

    public static boolean isComma(Token token) {
        return null != token && (token.getType() == TokenType.COMMA);
    }

    public static boolean isData(Token token) {
        return null != token && (token.getType() == TokenType.INTEGER
                || token.getType() == TokenType.STRING
                || token.getType() == TokenType.BOOL
                || token.getType() == TokenType.IDENTIFIER);
    }

    public static boolean isInteger(Token token) {
        return null != token && (token.getType() == TokenType.INTEGER);
    }

    public static boolean isString(Token token) {
        return null != token && (token.getType() == TokenType.STRING);
    }

    public static boolean isBracket(Token token) {
        return null != token && (token.getType() == TokenType.LEFT_BRACKET
                || token.getType() == TokenType.RIGHT_BRACKET);
    }

    public static boolean isLeftBracket(Token token) {
        return null != token && (token.getType() == TokenType.LEFT_BRACKET);
    }

    public static boolean isRightBracket(Token token) {
        return null != token && (token.getType() == TokenType.RIGHT_BRACKET);
    }

    public static boolean isBigBracket(Token token) {
        return null != token && (token.getType() == TokenType.BIG_LEFT_BRACKET
                || token.getType() == TokenType.BIG_RIGHT_BRACKET);
    }

    public static boolean isBigLeftBracket(Token token) {
        return null != token && token.getType() == TokenType.BIG_LEFT_BRACKET;
    }

    public static boolean isBigRightBracket(Token token) {
        return null != token && token.getType() == TokenType.BIG_RIGHT_BRACKET;
    }

    public static boolean isSemicolon(Token token) {
        return null != token && token.getType() == TokenType.SEMICOLON;
    }

    public static boolean isOperator(Token token) {
        return null != token && (token.getType() == TokenType.PLUS
                || token.getType() == TokenType.MINUS
                || token.getType() == TokenType.MUL
                || token.getType() == TokenType.DIV
                || token.getType() == TokenType.MOD);
    }

    public static boolean isCompareOperator(Token token) {
        return null != token && (
                token.getType() == TokenType.EQ
                        || token.getType() == TokenType.NEQ
                        || token.getType() == TokenType.GT
                        || token.getType() == TokenType.GTE
                        || token.getType() == TokenType.LT
                        || token.getType() == TokenType.LTE
        );
    }

    public static boolean isBoolOperator(Token token) {
        return null != token && (
                token.getType() == TokenType.AND
                        || token.getType() == TokenType.OR
                        || token.getType() == TokenType.NOT
        );
    }

    public static boolean isIf(Token token) {
        return null != token && token.getType() == TokenType.IF;
    }

    public static boolean isElse(Token token) {
        return null != token && token.getType() == TokenType.ELSE;
    }

    public static boolean isPlus(Token token) {
        return null != token && token.getType() == TokenType.PLUS;
    }

    public static boolean isMinus(Token token) {
        return null != token && token.getType() == TokenType.MINUS;
    }

    public static boolean isMul(Token token) {
        return null != token && token.getType() == TokenType.MUL;
    }

    public static boolean isDiv(Token token) {
        return null != token && token.getType() == TokenType.DIV;
    }

    public static boolean isMod(Token token) {
        return null != token && token.getType() == TokenType.MOD;
    }

    public static boolean isVar(Token token) {
        return null != token && token.getType() == TokenType.VAR;
    }

    public static boolean isLet(Token token) {
        return null != token && TokenType.LET == token.getType();
    }

    public static boolean isIdentifier(Token token) {
        return null != token && token.getType() == TokenType.IDENTIFIER;
    }

    public static boolean isAssign(Token token) {
        return null != token && token.getType() == TokenType.ASSIGN;
    }

    public static boolean isReturn(Token token) {
        return null != token && token.getType() == TokenType.RETURN;
    }

    public static boolean isFunction(Token token) {
        return null != token && token.getType() == TokenType.FUNCTION;
    }

    public static boolean isEOF(Token token) {
        return null != token && token.getType() == TokenType.EOF;
    }
}
