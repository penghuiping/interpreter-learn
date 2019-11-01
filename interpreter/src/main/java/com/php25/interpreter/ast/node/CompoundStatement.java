package com.php25.interpreter.ast.node;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/17 14:36
 */
@Setter
@Getter
@AllArgsConstructor
public class CompoundStatement extends AST {

    private Token bigLeftBracket;

    private AST statementList;

    private Token bigRightBracket;
}
