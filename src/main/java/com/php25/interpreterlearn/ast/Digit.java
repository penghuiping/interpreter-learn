package com.php25.interpreterlearn.ast;

import com.php25.interpreterlearn.bo.Token;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/16 09:29
 */
@Setter
@Getter
public class Digit extends AST {

    private Token token;

    public Digit(Token token) {
        this.token = token;
    }
}
