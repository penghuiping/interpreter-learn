package com.php25.regex;

/**
 * @author penghuiping
 * @date 2019/10/9 21:20
 */
enum TokenType {
    /**
     * 操作符 .,|,*
     */
    CONCAT, UNION, CLOSURE,

    /**
     * 整形数字 0,1,2,4,5,6,7,8,9
     */
    DIGIT,

    /**
     * 左括号,右括号
     */
    LEFT_BRACKET, RIGHT_BRACKET,

    /**
     * [A-Za-z]
     */
    ALPHABET,

    /**
     * 空格
     */
    SPACE,

    OTHERS,
}
