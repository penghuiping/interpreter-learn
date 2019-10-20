package com.php25.interpreterlearn.regex;

/**
 * @author penghuiping
 * @date 2019/10/14 18:09
 */
class Tokens {


    public static boolean isAlphabet(Token token) {
        return token.getType() == TokenType.ALPHABET;
    }

    public static boolean isDigit(Token token) {
        return token.getType() == TokenType.DIGIT;
    }

    public static boolean isLeftBracket(Token token) {
        return token.getType() == TokenType.LEFT_BRACKET;
    }

    public static boolean isRightBracket(Token token) {
        return token.getType() == TokenType.RIGHT_BRACKET;
    }


    public static boolean isConcat(Token token) {
        return token.getType() == TokenType.CONCAT;
    }

    public static boolean isClosure(Token token) {
        return token.getType() == TokenType.CLOSURE;
    }

    public static boolean isUnion(Token token) {
        return token.getType() == TokenType.UNION;
    }

    public static boolean isSpace(Token token) {
        return token.getType() == TokenType.SPACE;
    }
}
