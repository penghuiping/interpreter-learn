package com.php25.interpreterlearn.ast.node;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 一元操作符: -3
 * @author penghuiping
 * @date 2019/10/17 10:18
 */
@Setter
@Getter
@AllArgsConstructor
public class UnaryOp extends AST {

    private Token op;

    private AST next;
}
