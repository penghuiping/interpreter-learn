package com.php25.interpreter.ast;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * variable -> identifier
 *
 * @author penghuiping
 * @date 2019/11/4 22:17
 */
@Getter
@Setter
@AllArgsConstructor
public class Variable extends AST {

    private Token identifier;
}
