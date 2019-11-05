package com.php25.interpreter.syntax.node;

import com.php25.interpreter.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * function_declare -> function_name function_params function_body
 *
 * @author penghuiping
 * @date 2019/11/5 13:27
 */
@Setter
@Getter
@AllArgsConstructor
public class FunctionDeclare extends AST {

    private AST functionName;

    private AST functionParams;

    private AST functionBody;
}
