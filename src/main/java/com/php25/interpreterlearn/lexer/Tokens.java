package com.php25.interpreterlearn.lexer;

/**
 * @author penghuiping
 * @date 2019/10/14 18:09
 */
public class Tokens {


    public static boolean isData(Token token) {
        if (token.getType() == TokenType.digit || token.getType() == TokenType.string
                || token.getType() == TokenType.bool || token.getType() == TokenType.term) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDigit(Token token) {
        if (token.getType() == TokenType.digit) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBracket(Token token) {
        if (token.getType() == TokenType.leftBracket || token.getType() == TokenType.rightBracket) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLeftBracket(Token token) {
        if (token.getType() == TokenType.leftBracket) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRightBracket(Token token) {
        if (token.getType() == TokenType.rightBracket) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOperator(Token token) {
        if (token.getType() == TokenType.plus
                || token.getType() == TokenType.minus
                || token.getType() == TokenType.mul
                || token.getType() == TokenType.div
                || token.getType() == TokenType.mod
        ) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPlus(Token token) {
        return token.getType() == TokenType.plus;
    }

    public static boolean isMinus(Token token) {
        return token.getType() == TokenType.minus;
    }

    public static boolean isMul(Token token) {
        return token.getType() == TokenType.mul;
    }

    public static boolean isDiv(Token token) {
        return token.getType() == TokenType.div;
    }

    public static boolean isMod(Token token) {
        return token.getType() == TokenType.mod;
    }


}
