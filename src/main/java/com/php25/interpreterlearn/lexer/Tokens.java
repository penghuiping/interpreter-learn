package com.php25.interpreterlearn.lexer;

/**
 * @author penghuiping
 * @date 2019/10/14 18:09
 */
public class Tokens {


    public static boolean isData(Token token) {
        return token.getType() == TokenType.INTEGER
                || token.getType() == TokenType.STRING
                || token.getType() == TokenType.BOOL
                || token.getType() == TokenType.IDENTIFIER;
    }

    public static boolean isInteger(Token token) {
        return token.getType() == TokenType.INTEGER;
    }

    public static boolean isBracket(Token token) {
        return token.getType() == TokenType.LEFT_BRACKET
                || token.getType() == TokenType.RIGHT_BRACKET;
    }

    public static boolean isLeftBracket(Token token) {
        return token.getType() == TokenType.LEFT_BRACKET;
    }

    public static boolean isRightBracket(Token token) {
        return token.getType() == TokenType.RIGHT_BRACKET;
    }

    public static boolean isBigBracket(Token token) {
        return token.getType() == TokenType.BIG_LEFT_BRACKET
                || token.getType() == TokenType.BIG_RIGHT_BRACKET;
    }

    public static boolean isBigLeftBracket(Token token) {
        return token.getType() == TokenType.BIG_LEFT_BRACKET;
    }

    public static boolean isBigRightBracket(Token token) {
        return token.getType() == TokenType.BIG_RIGHT_BRACKET;
    }

    public static boolean isSemicolon(Token token) {
        return token.getType() == TokenType.SEMICOLON;
    }

    public static boolean isOperator(Token token) {
        return token.getType() == TokenType.PLUS
                || token.getType() == TokenType.MINUS
                || token.getType() == TokenType.MUL
                || token.getType() == TokenType.DIV
                || token.getType() == TokenType.MOD;
    }

    public static boolean isPlus(Token token) {
        return token.getType() == TokenType.PLUS;
    }

    public static boolean isMinus(Token token) {
        return token.getType() == TokenType.MINUS;
    }

    public static boolean isMul(Token token) {
        return token.getType() == TokenType.MUL;
    }

    public static boolean isDiv(Token token) {
        return token.getType() == TokenType.DIV;
    }

    public static boolean isMod(Token token) {
        return token.getType() == TokenType.MOD;
    }

    public static boolean isIdentifier(Token token) {
        return token.getType() == TokenType.IDENTIFIER;
    }

    public static boolean isAssign(Token token) {
        return token.getType() == TokenType.ASSIGN;
    }

}
