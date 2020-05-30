package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * function_invoke -> variable function_params
 *
 * @author penghuiping
 * @date 2019/11/5 15:13
 */
@Setter
@Getter
@AllArgsConstructor
public class FunctionInvoke extends AST {

    private AST variable;

    private AST functionParams;

}
