package com.php25.interpreterlearn.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/18 13:49
 */
@AllArgsConstructor
@Setter
@Getter
public class AstUnion extends AstRegex {

    private AstRegex leftExpr;

    private Token union;

    private AstRegex rightExpr;
}
