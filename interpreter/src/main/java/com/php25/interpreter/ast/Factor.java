package com.php25.interpreter.ast;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * factor -> factor0 |variable| LeftParenthesis expr RightParenthesis
 *
 * @author penghuiping
 * @date 2019/11/4 15:45
 */
@Getter
@Setter
@AllArgsConstructor
public class Factor extends AST {

    private AST ast;
}