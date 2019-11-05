package com.php25.interpreter.ast;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * program  -> statement_list
 *
 * @author penghuiping
 * @date 2019/11/4 22:39
 */
@Setter
@Getter
@AllArgsConstructor
public class Program extends AST {

    private AST statementList;
}
