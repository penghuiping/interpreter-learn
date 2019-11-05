package com.php25.interpreter.ast;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * factor0  -> integer|string
 * @author penghuiping
 * @date 2019/10/16 09:29
 */
@Setter
@Getter
@AllArgsConstructor
public class Factor0 extends AST {

    /**
     * integer,string
     */
    private Token token;




}
