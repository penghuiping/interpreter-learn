package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * expr -> term ((PLUS | MINUS) term)*
 *
 * @author penghuiping
 * @date 2019/10/16 09:29
 */
@Getter
@Setter
@AllArgsConstructor
public class Expr extends AST {

    private AST leftTerm;

    private Token op;

    private AST rightTerm;
}
