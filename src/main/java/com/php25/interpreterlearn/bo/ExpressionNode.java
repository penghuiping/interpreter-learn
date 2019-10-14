package com.php25.interpreterlearn.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/11 09:22
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpressionNode {

    private ExpressionNode parent;

    private ExpressionNode left;

    private ExpressionNode right;

    private Token token;
}
