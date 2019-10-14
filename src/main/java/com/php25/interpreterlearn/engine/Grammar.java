package com.php25.interpreterlearn.engine;

import com.php25.interpreterlearn.bo.Token;
import com.php25.interpreterlearn.constant.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/11 10:11
 */
public class Grammar {

    /**
     * 表达式语法验证
     *
     * @param tokens
     * @return
     */
    public static List<Token> validateExpression(List<Token> tokens, int position) {

        List<Token> newTokens = new ArrayList<>();

        Token token0 = tokens.get(position++);
        if (token0.getType() == TokenType.term || isData(token0.getType())) {
            newTokens.add(token0);
        } else {
            return null;
        }

        for (int i = position; i < tokens.size(); ) {
            if ((position + 2) > tokens.size()) {
                return newTokens.size() >= 3 ? newTokens : null;
            }

            Token token1 = tokens.get(position++);
            if (token1.getType() == TokenType.operator
                    && ("+".equals(token1.getValue())
                    || "-".equals(token1.getValue())
                    || "*".equals(token1.getValue())
                    || "/".equals(token1.getValue())
                    || "%".equals(token1.getValue()))
            ) {

            } else {
                return newTokens.size() >= 3 ? newTokens : null;
            }

            Token token2 = tokens.get(position++);
            if (token2.getType() == TokenType.term || isData(token2.getType())) {

            } else {
                return newTokens.size() >= 3 ? newTokens : null;
            }

            newTokens.add(token1);
            newTokens.add(token2);

            i = position;
        }

        return newTokens.size() >= 3 ? newTokens : null;

    }


    private static boolean isData(TokenType tokenType) {
        if (tokenType == TokenType.digit || tokenType == TokenType.string || tokenType == TokenType.bool) {
            return true;
        } else {
            return false;
        }
    }
}
