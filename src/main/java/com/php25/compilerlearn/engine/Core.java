package com.php25.compilerlearn.engine;

import com.php25.compilerlearn.exception.Exceptions;

/**
 * @author penghuiping
 * @date 2019/10/11 08:56
 */
public class Core {

    public static int express(String operator, int v1, int v2) {
        switch (operator) {
            case "+":
                return v1 + v2;
            case "-":
                return v1 - v2;
            case "*":
                return v1 * v2;
            case "/":
                return v1 / v2;
            case "%":
                return v1 % v2;
            default:
                throw Exceptions.throwIllegalStateException("不支持操作符：" + operator);
        }
    }
}
