package com.php25.interpreterlearn.ast;

import com.php25.interpreterlearn.bo.Token;
import com.php25.interpreterlearn.exception.Exceptions;

import java.util.function.Consumer;

/**
 * @author penghuiping
 * @date 2019/10/16 11:25
 */
public class Asts {

    /**
     * 前序遍历
     *
     * @param binaryTreeNode
     * @param consumer
     */
    public static void beforeOrderTraversalTree(AST binaryTreeNode, Consumer<Token> consumer) {
        if (binaryTreeNode instanceof BinOp) {
            BinOp binOp = (BinOp) binaryTreeNode;
            AST left = binOp.getLeft();
            AST right = binOp.getRight();

            consumer.accept(binOp.getOp());

            if (left != null) {
                beforeOrderTraversalTree(left, consumer);
            }

            if (right != null) {
                beforeOrderTraversalTree(right, consumer);
            }
        } else if (binaryTreeNode instanceof Digit) {
            Digit digit = (Digit) binaryTreeNode;
            consumer.accept(digit.getToken());
        } else {
            error();
        }
    }

    /**
     * 中序遍历
     *
     * @param binaryTreeNode
     * @param consumer
     */
    public static void middleOrderTraversalTree(AST binaryTreeNode, Consumer<Token> consumer) {
        if (binaryTreeNode instanceof BinOp) {
            BinOp binOp = (BinOp) binaryTreeNode;
            AST left = binOp.getLeft();
            AST right = binOp.getRight();

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
        } else {
            error();
        }
    }

    /**
     * 后序遍历
     *
     * @param binaryTreeNode
     * @param consumer
     */
    public static void afterOrderTraversalTree(AST binaryTreeNode, Consumer<Token> consumer) {
        if (binaryTreeNode instanceof BinOp) {
            BinOp binOp = (BinOp) binaryTreeNode;
            AST left = binOp.getLeft();
            AST right = binOp.getRight();

            if (left != null) {
                afterOrderTraversalTree(left, consumer);
            }


            if (right != null) {
                afterOrderTraversalTree(right, consumer);
            }

            consumer.accept(binOp.getOp());

        } else if (binaryTreeNode instanceof Digit) {
            Digit digit = (Digit) binaryTreeNode;
            consumer.accept(digit.getToken());
        } else {
            error();
        }
    }


    public static void error() {
        throw Exceptions.throwIllegalStateException("syntax error");
    }
}
