package com.php25.interpreterlearn.lexer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/9 20:52
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    private int row;

    private int column;
}
