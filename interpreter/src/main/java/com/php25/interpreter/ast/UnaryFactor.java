package com.php25.interpreter.ast;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 一元操作符: -3
 * <p>
 * unary_factor          -> (PLUS | MINUS) factor
 *
 * @author penghuiping
 * @date 2019/10/17 10:18
 */
@Setter
@Getter
@AllArgsConstructor
public class UnaryFactor extends AST {

    private Token minusOrPlus;

    private AST factor;
}
