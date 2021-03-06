package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * function_body -> {statement_list (return variable)?}
 *
 * @author penghuiping
 * @date 2019/11/5 11:08
 */
@Setter
@Getter
@AllArgsConstructor
public class FunctionBody extends AST {

    private AST statementList;

    private AST returnValue;
}
