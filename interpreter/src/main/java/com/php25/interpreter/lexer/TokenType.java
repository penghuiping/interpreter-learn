package com.php25.interpreter.lexer;

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
     * 关键字  if,else,for,while,var,break,continue,let,return,function
     */
    IF, ELSE, FOR, WHILE, VAR, BREAK, CONTINUE, LET, RETURN, FUNCTION,

    /**
     * 赋值 =
     */
    ASSIGN,

    /**
     * 大括号 {,}
     */
    BIG_LEFT_BRACKET, BIG_RIGHT_BRACKET,

    /**
     * !,&&,||,==,!=,>,<,>=,<=,
     */
    NOT, AND, OR, EQ, NEQ, GT, LT, GTE, LTE,

    /**
     * 标识
     */
    IDENTIFIER,

    /**
     * 分隔符  ;,\n
     */
    SEPARATOR, SEMICOLON, COMMA, EOF,

    /**
     * "\/*,*\/,//"
     */
    LEFT_BLOCK_COMMENT, RIGHT_BLOCK_COMMENT, ONE_LINE_COMMENT

}
