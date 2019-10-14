package com.php25.interpreterlearn.bo;

import com.php25.interpreterlearn.constant.TokenType;
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
public class Token {

    private String value;

    private TokenType type;

    private Position position;
}
