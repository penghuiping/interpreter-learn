package com.php25.interpreterlearn.ast;

import com.php25.interpreterlearn.lexer.Token;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/16 09:29
 */
@Getter
@Setter
public class BinOp extends AST {

    private AST left;

    private Token op;

    private AST right;

    public BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
}
