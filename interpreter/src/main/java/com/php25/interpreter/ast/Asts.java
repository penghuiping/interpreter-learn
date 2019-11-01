package com.php25.interpreter.ast;

import com.php25.interpreter.ast.node.BinOp;
import com.php25.interpreter.ast.node.Digit;
import com.php25.interpreter.ast.node.UnaryOp;
import com.php25.exception.Exceptions;
import com.php25.interpreter.lexer.Token;

import java.util.function.Consumer;

/**
 * @author penghuiping
 * @date 2019/10/16 11:25
 */
public class Asts {

    /**
     * 中序遍历
     *
     * @param binaryTreeNode
     * @param consumer
     */
    public static void middleOrderTraversalTree(AST binaryTreeNode, Consumer<Token> consumer) {
        if (binaryTreeNode instanceof BinOp) {
            BinOp binOp = (BinOp) binaryTreeNode;
            AST left = binOp.getLeftExpr();
            AST right = binOp.getRightExpr();

            if (left != null) {
                middleOrderTraversalTree(left, consumer);
            }

            consumer.accept(binOp.getOp());

            if (right != null) {
                middleOrderTraversalTree(right, consumer);
            }
        } else if (binaryTreeNode instanceof Digit) {
            Digit digit = (Digit) binaryTreeNode;
            consumer.accept(digit.getToken());
        } else if (binaryTreeNode instanceof UnaryOp) {
            UnaryOp unaryOp = (UnaryOp) binaryTreeNode;
            consumer.accept(unaryOp.getOp());
            middleOrderTraversalTree(unaryOp.getNext(), consumer);
        } else {
            error();
        }
    }

    public static void error() {
        throw Exceptions.throwIllegalStateException("syntax error");
    }
}
