package com.php25.interpreterlearn.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/18 13:36
 */
@Setter
@Getter
@AllArgsConstructor
public class AstConcat extends AstRegex {

    private AstRegex leftExpr;

    private Token concat;

    private AstRegex rightExpr;
}
