package com.php25.compilerlearn.engine;

import com.php25.compilerlearn.bo.Token;
import com.php25.compilerlearn.bo.ExpressionNode;
import com.php25.compilerlearn.constant.GrammarType;
import com.php25.compilerlearn.constant.TokenType;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/10 17:53
 */
public class Parser {

    public static void parse(List<ExpressionNode> treeNodes, List<GrammarType> grammarTypes) {
        for (int i = 0; i < treeNodes.size(); i++) {
            ExpressionNode treeNode = treeNodes.get(i);
            GrammarType grammarType = grammarTypes.get(i);
            switch (grammarType) {
                case expression:
                    expression(treeNode);
                    break;
                default:
                    break;
            }
        }
    }

    public static int expression(ExpressionNode treeNode) {
        if (treeNode.getParent().getRight().getToken().getType().equals(TokenType.operator)) {
            //如果遇到了操作符，优先级必定高，所以先执行子树
            ExpressionNode currentNode = treeNode.getParent().getRight();

            while (null != currentNode.getLeft()) {
                currentNode = currentNode.getLeft();
            }

            expression(currentNode);

            return Core.express(treeNode.getParent().getToken().getValue()
                    , Integer.parseInt(treeNode.getToken().getValue())
                    , expression(currentNode));
        } else {
            int newValue = Core.express(treeNode.getParent().getToken().getValue()
                    , Integer.parseInt(treeNode.getToken().getValue())
                    , Integer.parseInt(treeNode.getParent().getRight().getToken().getValue()));
            if (treeNode.getParent().getParent() == null) {
                return newValue;
            } else {
                ExpressionNode treeNode1 = new ExpressionNode(treeNode.getParent().getParent(), null, null, new Token(newValue + "", TokenType.digit, null));
                treeNode.getParent().getParent().setLeft(treeNode1);
                return expression(treeNode1);
            }

        }
    }
}
