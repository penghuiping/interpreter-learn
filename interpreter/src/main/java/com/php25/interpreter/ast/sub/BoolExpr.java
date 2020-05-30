package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/5/30 19:40
 */
@Setter
@Getter
@AllArgsConstructor
public class BoolExpr extends AST {

    private AST leftExpr0;

    private Token op;

    private AST rightExpr0;
}
