package com.php25.interpreterlearn.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/9 20:49
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Token {

    private String value;

    private TokenType type;

    private Position position;
}
