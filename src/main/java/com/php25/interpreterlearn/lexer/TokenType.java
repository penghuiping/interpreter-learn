package com.php25.interpreterlearn.lexer;

/**
 * @author penghuiping
 * @date 2019/10/9 21:20
 */
public enum TokenType {
    /**
     * 操作符 +,-,*,/,%
     */
    PLUS, MINUS, MUL, DIV, MOD,

    /**
     * 1. 整形数字 0,1,2,4,5,6,7,8,9
     * 2. 布尔值 true,false
     * 3. 字符串 "hello"
     */
    INTEGER, BOOL, STRING,

    /**
     * 左括号,右括号
     */
    LEFT_BRACKET, RIGHT_BRACKET,


    /**
     * 关键字  if,else,for,while,var,break,continue
     */
    IF,ELSE,FOR,WHILE,VAR,BREAK,CONTINUE,

    /**
     * 赋值 =
     */
    ASSIGN,

    /**
     * 大括号 {,}
     */
    BIG_LEFT_BRACKET,BIG_RIGHT_BRACKET,

    /**
     * &&,||,==,!=,>,<,>=,<=,
     */
    BOOL_OPERATOR,

    /**
     * 标识
     */
    IDENTIFIER,

    /**
     * 分隔符  ,;,\n
     */
    SEPARATOR,SEMICOLON,



}
