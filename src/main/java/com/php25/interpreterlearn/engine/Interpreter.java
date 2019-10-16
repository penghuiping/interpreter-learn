package com.php25.interpreterlearn.engine;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.ast.BinOp;
import com.php25.interpreterlearn.ast.Digit;
import com.php25.interpreterlearn.exception.Exceptions;

/**
 * @author penghuiping
 * @date 2019/10/16 10:45
 */
public class Interpreter {

    public Integer visit(AST ast_) {
        if (ast_ instanceof BinOp) {
            return visitBinOp(ast_);
        } else if (ast_ instanceof Digit) {
            return visitNum(ast_);
        } else {
            throw Exceptions.throwIllegalStateException("不支持此类型的AST");
        }
    }


    private Integer visitBinOp(AST ast_) {
        BinOp binOp = (BinOp) ast_;
        AST left = binOp.getLeft();
        AST right = binOp.getRight();
        return Core.express(binOp.getOp().getValue(), visit(left), visit(right));
    }

    private Integer visitNum(AST ast_) {
        Digit digit = (Digit) ast_;
        return Integer.parseInt(digit.getToken().getValue());
    }


}
