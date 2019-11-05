package com.php25.interpreter.ast;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * term                  -> unary_factor ((MUL | DIV | MOD) unary_factor)*
 *
 * @author penghuiping
 * @date 2019/11/4 15:51
 */
@Getter
@Setter
@AllArgsConstructor
public class Term extends AST {

    private AST leftUnaryFactor;

    private Token op;

    private AST rightUnaryFactor;
}
