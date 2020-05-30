package com.php25.interpreter.ast.sub;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.lexer.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * BasicType  -> integer|string
 * @author penghuiping
 * @date 2019/10/16 09:29
 */
@Setter
@Getter
@AllArgsConstructor
public class BasicType extends AST {

    /**
     * integer,string
     */
    private Token token;




}
