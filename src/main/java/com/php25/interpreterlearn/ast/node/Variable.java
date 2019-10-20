package com.php25.interpreterlearn.ast.node;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/17 11:24
 */
@Setter
@Getter
@AllArgsConstructor
public class Variable extends AST {

    private Token token;
}
