package com.php25.interpreter.interpreter;

import com.php25.exception.Exceptions;
import com.php25.interpreter.ast.AST;
import com.php25.interpreter.engine.GlobalMemory;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.lexer.Tokens;
import com.php25.interpreter.ast.sub.AssignStatement;
import com.php25.interpreter.ast.sub.Expr;
import com.php25.interpreter.ast.sub.BasicType;
import com.php25.interpreter.ast.sub.StatementList;
import com.php25.interpreter.ast.sub.UnaryFactor;
import com.php25.interpreter.ast.sub.VariableDeclare;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/16 10:45
 */
public class InterpreterParser {


    public Object visit(AST ast) {
        if (ast instanceof Expr) {
            return visitBinOp(ast);
        } else if (ast instanceof BasicType) {
            return visitDigit(ast);
        } else if (ast instanceof UnaryFactor) {
            return visitUnaryOp(ast);
        } else if (ast instanceof AssignStatement) {
            return visitAssignStatement(ast);
        } else if (ast instanceof StatementList) {
            return visitStatementList(ast);
        } else if (ast instanceof VariableDeclare) {
            return visitVarDeclare(ast);
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


    private Integer visitDigit(AST ast) {
        BasicType digit = (BasicType) ast;
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
        List<Token> ops = unaryOp.getMinusOrPlus();

        for (int i = ops.size() - 1; i >= 0; i--) {
            Token op = ops.get(i);
            if (Tokens.isMinus(op)) {
                value = -value;
            } else {
                value = +value;
            }
        }
        return value;
    }


    private String visitVarDeclare(AST ast) {
        VariableDeclare var = (VariableDeclare) ast;
        return var.getVariables().get(0).getIdentifier().getValue();
    }


}
