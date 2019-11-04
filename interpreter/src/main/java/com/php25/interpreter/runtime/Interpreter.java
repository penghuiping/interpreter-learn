package com.php25.interpreter.runtime;

import com.php25.exception.Exceptions;
import com.php25.interpreter.AST;
import com.php25.interpreter.engine.GlobalMemory;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.lexer.Tokens;
import com.php25.interpreter.syntax.node.AssignStatement;
import com.php25.interpreter.syntax.node.CompoundStatement;
import com.php25.interpreter.syntax.node.Expr;
import com.php25.interpreter.syntax.node.Factor0;
import com.php25.interpreter.syntax.node.StatementList;
import com.php25.interpreter.syntax.node.UnaryFactor;
import com.php25.interpreter.syntax.node.VariableDeclare;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/16 10:45
 */
public class Interpreter {


    public Object visit(AST ast) {
        if (ast instanceof Expr) {
            return visitBinOp(ast);
        } else if (ast instanceof Factor0) {
            return visitDigit(ast);
        } else if (ast instanceof UnaryFactor) {
            return visitUnaryOp(ast);
        } else if (ast instanceof AssignStatement) {
            return visitAssignStatement(ast);
        } else if (ast instanceof CompoundStatement) {
            return visitCompoundStatement(ast);
        } else if (ast instanceof StatementList) {
            return visitStatementList(ast);
        } else if (ast instanceof VariableDeclare) {
            return visitVar(ast);
        } else {
            throw Exceptions.throwIllegalStateException("不支持此类型的AST");
        }
    }


    private GlobalMemory visitAssignStatement(AST ast) {
        AssignStatement assignOp = (AssignStatement) ast;
        String key = (String) visit(assignOp.getVariable());
        Integer value = (Integer) visit(assignOp.getExpr());
        GlobalMemory.assignVar(key, value);
        return GlobalMemory.getInstance();
    }

    private Integer visitBinOp(AST ast) {
        Expr binOp = (Expr) ast;
        AST left = binOp.getLeftTerm();
        AST right = binOp.getRightTerm();
        return Core.express(binOp.getOp().getValue(), (Integer) visit(left), (Integer) visit(right));
    }


    private GlobalMemory visitCompoundStatement(AST ast) {
        CompoundStatement compoundStatement = (CompoundStatement) ast;
        AST statementList = compoundStatement.getStatementList();
        visit(statementList);
        return GlobalMemory.getInstance();
    }


    private Integer visitDigit(AST ast) {
        Factor0 digit = (Factor0) ast;
        return Integer.parseInt(digit.getToken().getValue());
    }

    private GlobalMemory visitStatementList(AST ast) {
        StatementList statementList = (StatementList) ast;
        List<AST> astList = statementList.getStatements();
        for (AST statement : astList) {
            visit(statement);
        }
        return GlobalMemory.getInstance();
    }


    private Integer visitUnaryOp(AST ast) {
        UnaryFactor unaryOp = (UnaryFactor) ast;
        AST next = unaryOp.getFactor();
        Integer value = (Integer) visit(next);
        Token op = unaryOp.getMinusOrPlus();

        if (Tokens.isMinus(op)) {
            return -value;
        } else {
            return value;
        }
    }


    private String visitVar(AST ast) {
        VariableDeclare var = (VariableDeclare) ast;
        return var.getIdentifiers().get(0).getValue();
    }


}
