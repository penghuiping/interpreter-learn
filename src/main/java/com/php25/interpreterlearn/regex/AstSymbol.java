package com.php25.interpreterlearn.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/18 13:23
 */
@AllArgsConstructor
@Getter
@Setter
public class AstSymbol extends AstRegex {
    private Token value;
}
