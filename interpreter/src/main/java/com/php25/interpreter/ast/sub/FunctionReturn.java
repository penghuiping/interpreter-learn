package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * function_return -> return variable SEMI
 *
 * @author penghuiping
 * @date 2020/5/30 16:23
 */
@Setter
@Getter
@AllArgsConstructor
public class FunctionReturn extends AST {

    private AST variable;

}
