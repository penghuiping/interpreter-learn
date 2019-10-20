package com.php25.interpreterlearn.ast.node;

import com.php25.interpreterlearn.ast.AST;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/17 15:13
 */
@Setter
@Getter
@AllArgsConstructor
public class StatementList extends AST {

    private List<AST> statements;

}
