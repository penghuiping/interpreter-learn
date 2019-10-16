package com.php25.interpreterlearn.engine;

import com.php25.interpreterlearn.bo.Position;
import com.php25.interpreterlearn.bo.Token;
import com.php25.interpreterlearn.constant.TokenType;
import com.php25.interpreterlearn.exception.Exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 1. 基本操作符 +,-,*,/,%,
 * 2. bool操作 &&,||,==,!=,>,<,>=,<=,
 * 3. 关键字 if,else,for,while,var,break,continue
 * 4. 小括号: (,)
 * 5. 大括号: {,}
 * 6. 分隔符: ,;,\n
 * 7. 赋值运算符:=
 *
 * @author penghuiping
 * @date 2019/10/9 20:43
 */
public class Lexer {

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
                    StringBuilder tokenValue = new StringBuilder();
                    getDigit(tokenValue, line.substring(i), row, 0);
                    Token token = new Token(tokenValue.toString(), TokenType.digit, new Position(row, col));
                    result.add(token);
                    i = i + tokenValue.length();
                } else if (cv == '\"') {
                    //字符串
                    col = i;
                    String str = line.substring(i);
                    int lastPosition = 0;

                    for (int j = 0; j < str.length(); j++) {
                        char s = str.charAt(j);
                        if (s == '\"' && j != 0) {
                            lastPosition = j;
                            break;
                        }
                    }

                    if (lastPosition == 0) {
                        //一行中没找到引号,就说明字符串写法不合法
                        throw Exceptions.throwIllegalStateException(String.format("lexical阶段出错,非法字符串:%s,位于第%d行,%d列", str, row, col));
                    }
                    String tokenValue = line.substring(i, i + lastPosition + 1);

                    i = i + tokenValue.length();

                    //去掉字符串中的双引号
                    tokenValue = tokenValue.replace("\"", "");
                    Token token = new Token(tokenValue, TokenType.string, new Position(row, col));
                    result.add(token);

                } else if (cv == '+' || cv == '-' || cv == '*' || cv == '/' || cv == '%') {
                    //基本运算符
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.operator, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == '&' || cv == '=' || cv == '|' || cv == '!' || cv == '>' || cv == '<') {
                    col = i;
                    StringBuilder tokenValue = new StringBuilder();
                    String lineTmp = line.substring(i);

                    if (cv == '=') {
                        //先判断 bool运算符
                        getBoolOperator(tokenValue, lineTmp, row, 0);
                        if (tokenValue.length() > 0) {
                            Token token = new Token(tokenValue.toString(), TokenType.boolOperator, new Position(row, col));
                            result.add(token);
                            i = i + tokenValue.length();
                        } else {
                            //在判断 assign =赋值运算符
                            if ('=' == lineTmp.charAt(0)) {
                                Token tokenAssign = new Token("=", TokenType.assign, new Position(row, col));
                                result.add(tokenAssign);
                                ++i;
                            }
                        }
                    } else {
                        //bool运算符
                        getBoolOperator(tokenValue, lineTmp, row, 0);
                        Token token = new Token(tokenValue.toString(), TokenType.boolOperator, new Position(row, col));
                        result.add(token);
                        i = i + tokenValue.length();
                    }
                } else if (cv == '(') {
                    //左括号
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.leftBracket, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == ')') {
                    //右括号
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.rightBracket, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == '{' || cv == '}') {
                    //大括号
                    col = i;
                    Token token = new Token(Character.toString(cv), TokenType.bigBracket, new Position(row, col));
                    result.add(token);
                    ++i;
                } else if (cv == ' ' || cv == ';' || cv == ',') {
                    //分隔符
                    switch (cv) {
                        case ' ': {
                            //分隔符-空格
                            col = i;
                            StringBuilder tokenValue = new StringBuilder();
                            getSpace(tokenValue, line.substring(i), row, 0);
                            Token token = new Token(tokenValue.toString(), TokenType.separator, new Position(row, col));
                            //result.add(token);
                            i = i + tokenValue.length();
                            break;
                        }
                        case ';': {
                            //分隔符-分号
                            col = i;
                            Token token = new Token(Character.toString(cv), TokenType.separator, new Position(row, col));
                            result.add(token);
                            ++i;
                            break;
                        }
                        case ',': {
                            //分隔符-逗号
                            col = i;
                            Token token = new Token(Character.toString(cv), TokenType.separator, new Position(row, col));
                            result.add(token);
                            ++i;
                            break;
                        }
                        default:

                    }
                } else if (Character.isLetter(cv) || cv == '_') {
                    //字符名称
                    col = i;
                    StringBuilder tokenValue = new StringBuilder();
                    getLabel(tokenValue, line.substring(i), row, 0);
                    Token token = new Token(tokenValue.toString(), TokenType.term, new Position(row, col));

                    //bool值处理
                    if ("true".equals(tokenValue.toString()) || "false".equals(tokenValue.toString())) {
                        token.setType(TokenType.bool);
                    }

                    //关键字处理
                    if ("var".equals(tokenValue.toString())
                            || "if".equals(tokenValue.toString())
                            || "else".equals(tokenValue.toString())
                            || "for".equals(tokenValue.toString())
                            || "while".equals(tokenValue.toString())
                            || "break".equals(tokenValue.toString())
                            || "continue".equals(tokenValue.toString())
                    ) {
                        token.setType(TokenType.keyword);
                    }

                    result.add(token);
                    i = i + tokenValue.length();
                }
            }
        }

        return result;
    }

    /**
     * 获取文本中的数字
     *
     * @param token
     * @param text
     * @param row
     * @param column
     */
    private static void getDigit(StringBuilder token, String text, int row, int column) {
        if (column < text.length()) {
            char v = text.charAt(column);
            if (Character.isDigit(v)) {
                token.append(v);
                getDigit(token, text, row, ++column);
            } else if (Character.isLetter(v)) {
                token.append(v);
                throw Exceptions.throwIllegalStateException(String.format("lexical阶段出错,非法数字:%s,位于第%d行,%d列", token, row, column));
            }
        }
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

    /**
     * 获取文本中的字符名称
     *
     * @param token
     * @param text
     * @param row
     * @param column
     */
    private static void getLabel(StringBuilder token, String text, int row, int column) {
        if (column < text.length()) {
            char v = text.charAt(column);
            if (Character.isLetter(v) || v == '_' || Character.isDigit(v)) {
                token.append(v);
                getLabel(token, text, row, ++column);
            } else if (v == '!' || v == '@' || v == '#' || v == '$' || v == '^' || v == '\\' || v == '.') {
                token.append(v);
                throw Exceptions.throwIllegalStateException(String.format("lexical阶段出错,非法字符名称:%s,位于第%d行,%d列", token, row, column));
            }
        }
    }


    /**
     * 获取文本中的布尔操作符 &&,||,==,!=,>,<,>=,<=,
     *
     * @param token
     * @param text
     * @param row
     * @param column
     */
    private static void getBoolOperator(StringBuilder token, String text, int row, int column) {
        if (column < text.length()) {
            char v0 = text.charAt(column);
            if ((column + 1) < text.length()) {
                char v1 = text.charAt(column + 1);
                if (v0 == '&' || v0 == '=' || v0 == '|') {
                    //处理bool操作符  &&,==,||
                    if (v0 == v1) {
                        token.append(v0).append(v1);
                    } else {
                        if (v0 != '=') {
                            throw Exceptions.throwIllegalStateException(String.format("lexical阶段出错,非法操作符:%s,位于第%d行,%d列", token, row, column));
                        }
                    }
                } else {
                    //处理bool操作符  >=,<=,!=
                    if ((v0 == '>' || v0 == '<' || v0 == '!') && v1 == '=') {
                        token.append(v0).append(v1);
                    } else if (v0 == '>' || v0 == '<') {
                        token.append(v0);
                    } else {
                        throw Exceptions.throwIllegalStateException(String.format("lexical阶段出错,非法操作符:%s,位于第%d行,%d列", token, row, column));
                    }

                }
            } else {
                //处理bool操作符 >,<,
                if (v0 == '>' || v0 == '<') {
                    token.append(v0);
                } else {
                    throw Exceptions.throwIllegalStateException(String.format("lexical阶段出错,非法操作符:%s,位于第%d行,%d列", token, row, column));
                }
            }
        }

        if (token.length() > 2) {
            throw Exceptions.throwIllegalStateException(String.format("lexical阶段出错,非法操作符:%s,位于第%d行,%d列", token, row, column));
        }
    }


}
