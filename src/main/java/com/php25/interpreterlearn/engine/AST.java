package com.php25.interpreterlearn.engine;

import com.php25.interpreterlearn.bo.Token;
import com.php25.interpreterlearn.bo.ExpressionNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author penghuiping
 * @date 2019/10/12 14:33
 */
public class AST {


    private static Map<String, Integer> expressPrivilege = new HashMap<>();

    static {
        expressPrivilege.put("-", 0);
        expressPrivilege.put("+", 0);
        expressPrivilege.put("*", 1);
        expressPrivilege.put("/", 1);
        expressPrivilege.put("%", 1);
    }

    /**
     * 表达式语法 AST
     *
     * @param tokens
     * @return
     */
    public static ExpressionNode expression(List<Token> tokens) {

        ExpressionNode tree = null;

        ExpressionNode previousNode = null;

        for (int i = 0; i < tokens.size(); i++) {
            ExpressionNode node = new ExpressionNode(null, null, null, tokens.get(i));
            if (null == tree) {
                tree = node;
            } else {
                switch (node.getToken().getType()) {
                    case operator:
                        //看看父节点的操作符权限，思想是找到与此node相同权限的上一个操作符
                        if (null == previousNode.getParent()) {
                            previousNode.setParent(node);
                            node.setLeft(previousNode);
                        } else if (expressPrivilege.get(node.getToken().getValue())
                                > expressPrivilege.get(previousNode.getParent().getToken().getValue())) {
                            //当前操作符节点的权限大于上一个节点父节点操作符
                            ExpressionNode parent = previousNode.getParent();
                            previousNode.setParent(node);
                            node.setLeft(previousNode);
                            parent.setRight(node);
                        } else {
                            previousNode.getParent().setParent(node);
                            node.setLeft(previousNode.getParent());
                        }
                        break;
                    case digit:
                        previousNode.setRight(node);
                        node.setParent(previousNode);
                        break;
                    case term:
                        previousNode.setRight(node);
                        node.setParent(previousNode);
                        break;
                    default:
                        break;
                }


            }
            previousNode = node;
        }
        return tree;
    }
}
