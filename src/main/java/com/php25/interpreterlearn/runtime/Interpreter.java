package com.php25.interpreterlearn.runtime;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.ast.node.AssignStatement;
import com.php25.interpreterlearn.ast.node.BinOp;
import com.php25.interpreterlearn.ast.node.CompoundStatement;
import com.php25.interpreterlearn.ast.node.Digit;
import com.php25.interpreterlearn.ast.node.StatementList;
import com.php25.interpreterlearn.ast.node.UnaryOp;
import com.php25.interpreterlearn.ast.node.Variable;
import com.php25.interpreterlearn.engine.SymbolTable;
import com.php25.interpreterlearn.exception.Exceptions;
import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.lexer.Tokens;

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


    private SymbolTable visitAssignStatement(AST ast) {
        AssignStatement assignOp = (AssignStatement) ast;
        String key = (String) visit(assignOp.getVarName());
        Integer value = (Integer) visit(assignOp.getExpr());
        SymbolTable symbolTable = new SymbolTable();
        SymbolTable._table.put(key, value);
        return symbolTable;
    }

    private Integer visitBinOp(AST ast) {
        BinOp binOp = (BinOp) ast;
        AST left = binOp.getLeftExpr();
        AST right = binOp.getRightExpr();
        return Core.express(binOp.getOp().getValue(), (Integer) visit(left), (Integer) visit(right));
    }


    private SymbolTable visitCompoundStatement(AST ast) {
        CompoundStatement compoundStatement = (CompoundStatement) ast;
        AST statementList = compoundStatement.getStatementList();
        visit(statementList);

        SymbolTable symbolTable = new SymbolTable();
        return symbolTable;
    }


    private Integer visitDigit(AST ast) {
        Digit digit = (Digit) ast;
        return Integer.parseInt(digit.getToken().getValue());
    }

    private SymbolTable visitStatementList(AST ast) {
        StatementList statementList = (StatementList) ast;
        List<AST> astList = statementList.getStatements();
        for (AST statement : astList) {
            visit(statement);
        }
        SymbolTable symbolTable = new SymbolTable();
        return symbolTable;
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
