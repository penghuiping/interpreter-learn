package com.php25.interpreter;

import com.php25.exception.Exceptions;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.syntax.node.AssignStatement;
import com.php25.interpreter.syntax.node.CompoundStatement;
import com.php25.interpreter.syntax.node.Expr;
import com.php25.interpreter.syntax.node.Factor;
import com.php25.interpreter.syntax.node.Factor0;
import com.php25.interpreter.syntax.node.StatementList;
import com.php25.interpreter.syntax.node.Term;
import com.php25.interpreter.syntax.node.UnaryFactor;

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
        if (binaryTreeNode instanceof AssignStatement) {
            AssignStatement assignStatement = (AssignStatement) binaryTreeNode;
            middleOrderTraversalTree(assignStatement.getVariable(),consumer);
        } else if (binaryTreeNode instanceof CompoundStatement) {

        } else if (binaryTreeNode instanceof Expr) {
            Expr binOp = (Expr) binaryTreeNode;
            AST left = binOp.getLeftTerm();
            AST right = binOp.getRightTerm();

            if (left != null) {
                middleOrderTraversalTree(left, consumer);
            }

            consumer.accept(binOp.getOp());

            if (right != null) {
                middleOrderTraversalTree(right, consumer);
            }
        } else if (binaryTreeNode instanceof Factor) {

        } else if (binaryTreeNode instanceof Factor0) {
            Factor0 digit = (Factor0) binaryTreeNode;
            consumer.accept(digit.getToken());
        } else if (binaryTreeNode instanceof StatementList) {

        } else if (binaryTreeNode instanceof Term) {

        } else if (binaryTreeNode instanceof UnaryFactor) {
            UnaryFactor unaryOp = (UnaryFactor) binaryTreeNode;
            consumer.accept(unaryOp.getMinusOrPlus());
            middleOrderTraversalTree(unaryOp.getFactor(), consumer);
        } else {
            error();
        }
    }

    public static void error() {
        throw Exceptions.throwIllegalStateException("syntax error");
    }
}
