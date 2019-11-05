package com.php25.interpreter.ast;

import com.php25.interpreter.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * statement_list  ->  (statement SEMI)+
 *
 * @author penghuiping
 * @date 2019/10/17 15:13
 */
@Setter
@Getter
@AllArgsConstructor
public class StatementList extends AST {

    private List<AST> statements;

}
