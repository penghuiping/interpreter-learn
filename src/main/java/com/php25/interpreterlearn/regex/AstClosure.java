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
public class AstClosure extends AstRegex {

    private AstRegex expr;

    private Token closure;

}
