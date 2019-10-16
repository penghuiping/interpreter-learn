package com.php25.interpreterlearn.engine;

import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.exception.Exceptions;
import com.php25.interpreterlearn.lexer.Tokens;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/11 10:11
 */
public class Grammar {

    /**
     * 表达式语法验证，包含或者不包含括号()
     * <p>
     * (avg(a)*2+1)/(123-2)
     *
     * @param tokens
     * @return
     */
    private static List<Token> validateBracket(List<Token> tokens, int position) {
        List<Token> newTokens = new ArrayList<>();
        Token token0 = tokens.get(position++);

        if (Tokens.isLeftBracket(token0)) {
            newTokens.add(token0);
            for (int i = position; i < tokens.size(); ) {
                Token token = tokens.get(i);
                if (Tokens.isLeftBracket(token)) {
                    List<Token> recycleTokens = validateBracket(tokens, i);
                    if (null != recycleTokens) {
                        newTokens.addAll(recycleTokens);
                        position = position + recycleTokens.size();
                        i = position;
                    }
                } else if (Tokens.isRightBracket(token)) {
                    newTokens.add(token);
                    i = ++position;
                    break;
                } else {
                    newTokens.add(token);
                    i = ++position;
                }
            }
            return newTokens;
        } else {
            return null;
        }
    }


    /**
     * 表达式语法分析
     * <p>
     * 1. 表达式开头规则
     * 表达式需要以 data、leftBracket类型token开头
     * 2. 连续两两token规则: data bracket operator
     * data->data no
     * data->leftBracket no
     * leftBracket->data ok
     * data->rightBracket ok
     * rightBracket->data no
     * data->operator ok
     * operator->data ok
     * leftBracket->leftBracket ok
     * leftBracket->rightBracket no
     * rightBracket->leftBracket no
     * leftBracket->operator no
     * operator->leftBracket ok
     * rightBracket->rightBracket ok
     * rightBracket->operator ok
     * operator->rightBracket no
     * operator->operator no
     * 3. 括号对称规则
     * 4. 结尾规则
     * 表达式不能以 operator结尾
     *
     * @param tokens
     * @param position
     * @return
     */
    public static List<Token> validateExpression(List<Token> tokens, int position) {
        List<Token> newTokens = new ArrayList<>();

        int leftBracketCount = 0;
        int rightBracketCount = 0;


        //1. 表达式开头规则: expression表达式必须要以 data、leftBracket类型的Token开头
        Token token0 = tokens.get(position++);
        if (Tokens.isData(token0)) {
            newTokens.add(token0);
        } else if (Tokens.isLeftBracket(token0)) {
            ++leftBracketCount;
            newTokens.add(token0);
        } else {
            return null;
        }

        //2. 连续两两token规则: data bracket operator
        Token previousToken = token0;
        for (int i = position; i < tokens.size(); ) {


            Token currentToken = tokens.get(position++);

            if (Tokens.isOperator(currentToken) || Tokens.isBracket(currentToken) || Tokens.isData(currentToken)) {
                if (Tokens.isLeftBracket(previousToken) && Tokens.isData(currentToken)) {
                    //leftBracket->data ok
                    newTokens.add(currentToken);
                } else if (Tokens.isData(previousToken) && Tokens.isRightBracket(currentToken)) {
                    // data->rightBracket ok
                    newTokens.add(currentToken);
                    ++rightBracketCount;
                } else if (Tokens.isData(previousToken) && Tokens.isOperator(currentToken)) {
                    //data->operator
                    newTokens.add(currentToken);
                } else if (Tokens.isOperator(previousToken) && Tokens.isData(currentToken)) {
                    //operator->data ok
                    newTokens.add(currentToken);
                } else if (Tokens.isLeftBracket(previousToken) && Tokens.isLeftBracket(currentToken)) {
                    //leftBracket->leftBracket ok
                    newTokens.add(currentToken);
                    ++leftBracketCount;
                } else if (Tokens.isOperator(previousToken) && Tokens.isLeftBracket(currentToken)) {
                    //operator->leftBracket ok
                    newTokens.add(currentToken);
                    ++leftBracketCount;
                } else if (Tokens.isRightBracket(previousToken) && Tokens.isRightBracket(currentToken)) {
                    //rightBracket->rightBracket ok
                    newTokens.add(currentToken);
                    ++rightBracketCount;
                } else if (Tokens.isRightBracket(previousToken) && Tokens.isOperator(currentToken)) {
                    //rightBracket->operator ok
                    newTokens.add(currentToken);
                } else {
                    throw Exceptions.throwIllegalStateException(String.format("Grammar分析阶段出错,表达式语法出错,位于%d行,%d列", currentToken.getPosition().getRow(), currentToken.getPosition().getColumn()));
                }

                i = position;
                previousToken = currentToken;
            } else {
                break;
            }
        }


        //3. 括号对称规则
        if (leftBracketCount != rightBracketCount) {
            //expression中bracket需要对称
            Token tmp = tokens.get(position - 1);
            throw Exceptions.throwIllegalStateException(String.format("Grammar分析阶段出错,左右括号不对称,位于%d行,%d列", tmp.getPosition().getRow(), tmp.getPosition().getColumn()));
        }

        //4. 结尾规则:表达式不能以 operator结尾
        Token lastOne = newTokens.get(newTokens.size() - 1);
        if (Tokens.isOperator(lastOne)) {
            throw Exceptions.throwIllegalStateException(String.format("Grammar分析阶段出错,表达式语法错误,位于%d行,%d列", lastOne.getPosition().getRow(), lastOne.getPosition().getColumn()));
        }


        //expression中token大于3意义
        return newTokens.size() >= 3 ? newTokens : null;
    }

}
