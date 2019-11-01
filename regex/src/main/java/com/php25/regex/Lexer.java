package com.php25.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 1. 操作符 .,|,*
 * 2. 整形数字 0,1,2,4,5,6,7,8,9
 * 3. 字母 [A-Za-z]
 * 4. 小括号: (,)
 *
 * @author penghuiping
 * @date 2019/10/9 20:43
 */
class Lexer {

    /**
     * 分词，把文章拆分成一个一个最小的词
     *
     * @param text
     * @return
     */
    public static List<Token> parse(String text) {
        List<Token> result = new ArrayList<>();
        int row = 0;
        Scanner scanner = new Scanner(text);
        while (scanner.hasNextLine()) {
            ++row;
            String line = scanner.nextLine();
            int col = 0;
            for (int i = 0; i < line.length(); ) {
                char cv = line.charAt(i);
                if (Character.isDigit(cv)) {
                    //数字
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.DIGIT, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == '|') {
                    //基本运算符
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.UNION, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == '.') {
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.CONCAT, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == '*') {
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.CLOSURE, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == '(') {
                    //左括号
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.LEFT_BRACKET, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == ')') {
                    //右括号
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.RIGHT_BRACKET, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (Character.isLetter(cv) || cv == '_') {
                    //字符名称
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.ALPHABET, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == ' ') {
                    //分隔符-空格
                    col = i;
                    StringBuilder tokenValue = new StringBuilder();
                    getSpace(tokenValue, line.substring(i), row, 0);
                    Token token = new Token(tokenValue.toString(), TokenType.SPACE, new Position(row, col));
                    //result.add(token);
                    i = i + tokenValue.length();
                } else {
                    //其他字符
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.OTHERS, new Position(row, col));
                    result.add(token);
                    ++i;
                }
            }
        }
        return fixConcatOp(result);
    }

    /**
     * .* no
     * *. yes
     * .| no
     * |. no
     * .( yes
     * (. no
     * .) no
     * ). yes
     *
     * @param tokens
     * @return
     */
    private static List<Token> fixConcatOp(List<Token> tokens) {
        List<Token> newTokens = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (Tokens.isDigit(token) || Tokens.isAlphabet(token) || Tokens.isOthers(token)
                    || Tokens.isClosure(token) || Tokens.isRightBracket(token)) {
                if (i + 1 < tokens.size()) {
                    Token forwardToken = tokens.get(i + 1);
                    if (Tokens.isLeftBracket(forwardToken)
                            || Tokens.isDigit(forwardToken)
                            || Tokens.isOthers(forwardToken)
                            || Tokens.isAlphabet(forwardToken)) {
                        newTokens.add(token);
                        Token dotToken = new Token(".", TokenType.CONCAT, null);
                        newTokens.add(dotToken);
                        continue;
                    }
                }
            }
            newTokens.add(token);
        }
        return newTokens;
    }

    /**
     * 获取文本中的空格符
     *
     * @param token
     * @param text
     * @param row
     * @param column
     */
    private static void getSpace(StringBuilder token, String text, int row, int column) {
        if (column < text.length()) {
            char v = text.charAt(column);
            if (v == ' ') {
                token.append(v);
                getSpace(token, text, row, ++column);
            }
        }
    }
}
