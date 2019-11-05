package com.php25.interpreter.syntax.node;

import com.php25.interpreter.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * function_name -> function variable
 * @author penghuiping
 * @date 2019/11/5 11:02
 */
@Setter
@Getter
@AllArgsConstructor
public class FunctionName extends AST {

    private AST variable;
}
