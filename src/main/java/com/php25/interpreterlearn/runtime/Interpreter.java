package com.php25.interpreterlearn.runtime;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.ast.BinOp;
import com.php25.interpreterlearn.ast.Digit;
import com.php25.interpreterlearn.ast.UnaryOp;
import com.php25.interpreterlearn.exception.Exceptions;
import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.lexer.Tokens;

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
        } else if (ast instanceof UnaryOp) {
            return visitUnaryOp(ast);
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

    private Integer visitUnaryOp(AST ast) {
        UnaryOp unaryOp = (UnaryOp) ast;
        AST next = unaryOp.getNext();
        Integer value = visit(next);
        Token op = unaryOp.getOp();

        if (Tokens.isMinus(op)) {
            return -value;
        } else {
            return value;
        }
    }


}
