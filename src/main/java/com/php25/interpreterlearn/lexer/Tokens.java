package com.php25.interpreterlearn.lexer;

/**
 * @author penghuiping
 * @date 2019/10/14 18:09
 */
public class Tokens {


    public static boolean isData(Token token) {
        if (token.getType() == TokenType.INTEGER || token.getType() == TokenType.STRING
                || token.getType() == TokenType.BOOL || token.getType() == TokenType.IDENTIFIER) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInteger(Token token) {
        if (token.getType() == TokenType.INTEGER) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBracket(Token token) {
        if (token.getType() == TokenType.LEFT_BRACKET || token.getType() == TokenType.RIGHT_BRACKET) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLeftBracket(Token token) {
        if (token.getType() == TokenType.LEFT_BRACKET) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRightBracket(Token token) {
        if (token.getType() == TokenType.RIGHT_BRACKET) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOperator(Token token) {
        if (token.getType() == TokenType.PLUS
                || token.getType() == TokenType.MINUS
                || token.getType() == TokenType.MUL
                || token.getType() == TokenType.DIV
                || token.getType() == TokenType.MOD
        ) {
            return true;
        } else {
            return false;
        }
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


}
