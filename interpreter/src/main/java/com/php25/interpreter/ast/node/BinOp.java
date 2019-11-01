package com.php25.interpreter.ast.node;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/16 09:29
 */
@Getter
@Setter
@AllArgsConstructor
public class BinOp extends AST {

    private AST leftExpr;

    private Token op;

    private AST rightExpr;
}
