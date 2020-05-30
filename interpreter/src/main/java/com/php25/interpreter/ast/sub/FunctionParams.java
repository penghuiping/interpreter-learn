package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * function_params -> LeftParenthesis (variable|basic_type)? (,(variable|basic_type))* RightParenthesis
 *
 * @author penghuiping
 * @date 2019/11/5 11:05
 */
@Setter
@Getter
@AllArgsConstructor
public class FunctionParams extends AST {

    private List<AST> params;
}
