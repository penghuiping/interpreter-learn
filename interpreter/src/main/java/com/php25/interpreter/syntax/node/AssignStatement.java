package com.php25.interpreter.syntax.node;

import com.php25.interpreter.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 赋值操作节点
 *
 * assign_statement      -> variable assign expr
 *
 * @author penghuiping
 * @date 2019/10/17 11:29
 */
@Setter
@Getter
@AllArgsConstructor
public class AssignStatement extends AST {

    private AST variable;

    private Token assign;

    private AST expr;
}
