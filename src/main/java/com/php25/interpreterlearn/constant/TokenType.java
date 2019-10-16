package com.php25.interpreterlearn.constant;

/**
 * @author penghuiping
 * @date 2019/10/9 21:20
 */
public enum TokenType {
    /**
     * 操作符 +,-,*,/,%,
     */
    operator,

    /**
     * 赋值 =
     */
    assign,

    /**
     * &&,||,==,!=,>,<,>=,<=,
     */
    boolOperator,

    /**
     * 数字 0,1,2,4,5,6,7,8,9
     */
    digit,

    /**
     * true,false
     */
    bool,

    /**
     * 字符串
     */
    string,

    /**
     * 术语
     */
    term,

    /**
     * 分隔符  ,;,\n
     */
    separator,

    /**
     * 关键字  if,else,for,while,var,break,continue
     */
    keyword,


    /**
     * 左括号
     */
    leftBracket,

    /**
     * 右括号
     */
    rightBracket,

    /**
     * 大括号 {,}
     */
    bigBracket,
}
