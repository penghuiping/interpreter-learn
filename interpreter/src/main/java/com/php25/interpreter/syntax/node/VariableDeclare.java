package com.php25.interpreter.syntax.node;

import com.php25.interpreter.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/17 11:24
 */
@Setter
@Getter
@AllArgsConstructor
public class VariableDeclare extends AST {
    private Token varOrLet;

    private List<Token> identifiers;
}
