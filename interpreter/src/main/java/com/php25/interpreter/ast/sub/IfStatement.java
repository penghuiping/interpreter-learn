package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/5/30 19:30
 */
@Getter
@Setter
@AllArgsConstructor
public class IfStatement extends AST {
    /**
     * if/else
     */
    private Token keyword1;

    /**
     * if
     */
    private Token keyword2;

    private AST boolExpr;

    private AST statementBody;
}
