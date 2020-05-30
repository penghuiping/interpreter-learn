package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/5/30 19:38
 */
@Getter
@Setter
@AllArgsConstructor
public class Expr0 extends AST {
    private AST leftExpr;

    private Token op;

    private AST rightExpr;
}
