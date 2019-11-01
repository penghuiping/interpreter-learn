package com.php25.interpreter.ast.node;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 赋值操作节点
 *
 * @author penghuiping
 * @date 2019/10/17 11:29
 */
@Setter
@Getter
@AllArgsConstructor
public class AssignStatement extends AST {

    private AST varName;

    private Token assign;

    private AST expr;
}
