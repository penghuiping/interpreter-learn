package com.php25.interpreterlearn.engine;

import com.php25.interpreterlearn.bo.Token;
import com.php25.interpreterlearn.constant.TokenType;

import java.util.List;

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
        if (token.getType() == TokenType.operator) {
            return true;
        } else {
            return false;
        }
    }



    public static void print(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.print(token.getValue());
        }
        System.out.println();
    }

}
