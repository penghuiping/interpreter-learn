package com.php25.interpreter.syntax.node;

import com.php25.interpreter.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * function_params -> LeftParenthesis variable? (,variable)* RightParenthesis
 *
 * @author penghuiping
 * @date 2019/11/5 11:05
 */
@Setter
@Getter
@AllArgsConstructor
public class FunctionParams extends AST {

    private List<AST> variables;
}
