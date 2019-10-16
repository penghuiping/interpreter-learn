package com.php25.interpreterlearn.runtime;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.ast.BinOp;
import com.php25.interpreterlearn.ast.Digit;
import com.php25.interpreterlearn.exception.Exceptions;

/**
 * @author penghuiping
 * @date 2019/10/16 10:45
 */
public class Interpreter {

    public Integer visit(AST ast) {
        if (ast instanceof BinOp) {
            return visitBinOp(ast);
        } else if (ast instanceof Digit) {
            return visitDigit(ast);
        } else {
            throw Exceptions.throwIllegalStateException("不支持此类型的AST");
        }
    }


    private Integer visitBinOp(AST ast) {
        BinOp binOp = (BinOp) ast;
        AST left = binOp.getLeft();
        AST right = binOp.getRight();
        return Core.express(binOp.getOp().getValue(), visit(left), visit(right));
    }

    private Integer visitDigit(AST ast) {
        Digit digit = (Digit) ast;
        return Integer.parseInt(digit.getToken().getValue());
    }


}
