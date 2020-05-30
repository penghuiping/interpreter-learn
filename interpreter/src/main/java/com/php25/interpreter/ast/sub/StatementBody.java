package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/5/30 19:42
 */
@Setter
@Getter
@AllArgsConstructor
public class StatementBody extends AST{

    private AST statementList;
}
