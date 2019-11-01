package com.php25.interpreter.runtime;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.ast.node.AssignStatement;
import com.php25.interpreter.ast.node.BinOp;
import com.php25.interpreter.ast.node.CompoundStatement;
import com.php25.interpreter.ast.node.Digit;
import com.php25.interpreter.ast.node.StatementList;
import com.php25.interpreter.ast.node.UnaryOp;
import com.php25.interpreter.ast.node.Variable;
import com.php25.interpreter.engine.GlobalMemory;
import com.php25.exception.Exceptions;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.lexer.Tokens;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/16 10:45
 */
public class Interpreter {


    public Object visit(AST ast) {
        if (ast instanceof BinOp) {
            return visitBinOp(ast);
        } else if (ast instanceof Digit) {
            return visitDigit(ast);
        } else if (ast instanceof UnaryOp) {
            return visitUnaryOp(ast);
        } else if (ast instanceof AssignStatement) {
            return visitAssignStatement(ast);
        } else if (ast instanceof CompoundStatement) {
            return visitCompoundStatement(ast);
        } else if (ast instanceof StatementList) {
            return visitStatementList(ast);
        } else if (ast instanceof Variable) {
            return visitVar(ast);
        } else {
            throw Exceptions.throwIllegalStateException("不支持此类型的AST");
        }
    }


    private GlobalMemory visitAssignStatement(AST ast) {
        AssignStatement assignOp = (AssignStatement) ast;
        String key = (String) visit(assignOp.getVarName());
        Integer value = (Integer) visit(assignOp.getExpr());
        GlobalMemory.assignVar(key, value);
        return GlobalMemory.getInstance();
    }

    private Integer visitBinOp(AST ast) {
        BinOp binOp = (BinOp) ast;
        AST left = binOp.getLeftExpr();
        AST right = binOp.getRightExpr();
        return Core.express(binOp.getOp().getValue(), (Integer) visit(left), (Integer) visit(right));
    }


    private GlobalMemory visitCompoundStatement(AST ast) {
        CompoundStatement compoundStatement = (CompoundStatement) ast;
        AST statementList = compoundStatement.getStatementList();
        visit(statementList);
        return GlobalMemory.getInstance();
    }


    private Integer visitDigit(AST ast) {
        Digit digit = (Digit) ast;
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
        UnaryOp unaryOp = (UnaryOp) ast;
        AST next = unaryOp.getNext();
        Integer value = (Integer) visit(next);
        Token op = unaryOp.getOp();

        if (Tokens.isMinus(op)) {
            return -value;
        } else {
            return value;
        }
    }


    private String visitVar(AST ast) {
        Variable var = (Variable) ast;
        return var.getToken().getValue();
    }


}
