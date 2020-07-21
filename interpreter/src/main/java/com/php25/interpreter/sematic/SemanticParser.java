package com.php25.interpreter.sematic;

import com.php25.exception.Exceptions;
import com.php25.interpreter.ast.AST;
import com.php25.interpreter.ast.sub.AssignStatement;
import com.php25.interpreter.ast.sub.BasicType;
import com.php25.interpreter.ast.sub.BoolExpr;
import com.php25.interpreter.ast.sub.Expr;
import com.php25.interpreter.ast.sub.Expr0;
import com.php25.interpreter.ast.sub.Factor;
import com.php25.interpreter.ast.sub.FunctionBody;
import com.php25.interpreter.ast.sub.FunctionDeclare;
import com.php25.interpreter.ast.sub.FunctionInvoke;
import com.php25.interpreter.ast.sub.FunctionName;
import com.php25.interpreter.ast.sub.FunctionParams;
import com.php25.interpreter.ast.sub.FunctionReturn;
import com.php25.interpreter.ast.sub.IfStatement;
import com.php25.interpreter.ast.sub.Program;
import com.php25.interpreter.ast.sub.StatementBody;
import com.php25.interpreter.ast.sub.StatementList;
import com.php25.interpreter.ast.sub.Term;
import com.php25.interpreter.ast.sub.UnaryFactor;
import com.php25.interpreter.ast.sub.Variable;
import com.php25.interpreter.ast.sub.VariableDeclare;
import com.php25.interpreter.lexer.Token;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/11/5 16:27
 */
public class SemanticParser {

    private SymbolTable currentScope;

    public SemanticParser() {
    }

    public void visit(AST ast) {
        if (null == ast) {
            return;
        }

        if (ast instanceof AssignStatement) {
            visitAssignStatement(ast);
        } else if (ast instanceof BasicType) {
            visitBasicType(ast);
        } else if (ast instanceof BoolExpr) {
            visitBoolExpr(ast);
        } else if (ast instanceof Expr) {
            visitExpr(ast);
        } else if (ast instanceof Expr0) {
            visitExpr0(ast);
        } else if (ast instanceof Factor) {
            visitFactor(ast);
        } else if (ast instanceof FunctionBody) {
            visitFunctionBody(ast);
        } else if (ast instanceof FunctionDeclare) {
            visitFunctionDeclare(ast);
        } else if (ast instanceof FunctionInvoke) {
            visitFunctionInvoke(ast);
        } else if (ast instanceof FunctionName) {
            visitFunctionName(ast);
        } else if (ast instanceof FunctionParams) {
            visitFunctionParams(ast);
        } else if (ast instanceof FunctionReturn) {
            visitFunctionReturn(ast);
        } else if (ast instanceof IfStatement) {
            visitIfStatement(ast);
        } else if (ast instanceof Program) {
            visitProgram(ast);
        } else if (ast instanceof StatementBody) {
            visitStatementBody(ast);
        } else if (ast instanceof StatementList) {
            visitStatementList(ast);
        } else if (ast instanceof Term) {
            visitTerm(ast);
        } else if (ast instanceof UnaryFactor) {
            visitUnaryFactor(ast);
        } else if (ast instanceof Variable) {
            visitVariable(ast);
        } else if (ast instanceof VariableDeclare) {
            visitVariableDeclare(ast);
        } else {
            throw Exceptions.throwIllegalStateException("不支持此类型的AST");
        }
    }

    private void visitAssignStatement(AST ast) {
        AssignStatement assignStatement = (AssignStatement) ast;
        visit(assignStatement.getExpr());
        visit(assignStatement.getVariable());
        Token token = assignStatement.getAssign();
    }

    private void visitBasicType(AST ast) {
        BasicType basicType = (BasicType) ast;
        Token token = basicType.getToken();
    }

    private void visitBoolExpr(AST ast) {
        BoolExpr boolExpr = (BoolExpr) ast;
        visit(boolExpr.getLeftExpr0());
        visit(boolExpr.getRightExpr0());
        Token token = boolExpr.getOp();
    }

    private void visitExpr(AST ast) {
        Expr expr = (Expr) ast;
        visit(expr.getLeftTerm());
        visit(expr.getRightTerm());
        Token token = expr.getOp();
    }

    private void visitExpr0(AST ast) {
        Expr0 expr0 = (Expr0) ast;
        visit(expr0.getLeftExpr());
        visit(expr0.getRightExpr());
        Token token = expr0.getOp();
    }

    private void visitFactor(AST ast) {
        Factor factor = (Factor) ast;
        visit(factor.getAst());
    }

    private void visitFunctionBody(AST ast) {
        FunctionBody functionBody = (FunctionBody) ast;
        visit(functionBody.getStatementList());
        visit(functionBody.getReturnValue());
    }

    private void visitFunctionDeclare(AST ast) {
        FunctionDeclare functionDeclare = (FunctionDeclare) ast;

        FunctionName functionName = (FunctionName) functionDeclare.getFunctionName();
        Variable variable = (Variable) functionName.getVariable();
        if (this.currentScope.exists(variable.getIdentifier().getValue())) {
            throw Exceptions.throwIllegalStateException("方法名:" + variable.getIdentifier().getValue() + "已存在");
        }
        Symbol symbol = new FunctionSymbol(variable.getIdentifier().getValue());
        this.currentScope.insert(symbol);

        visit(functionDeclare.getFunctionName());
        this.currentScope = new SymbolTable(this.currentScope);
        FunctionParams functionParams = (FunctionParams) functionDeclare.getFunctionParams();
        if (null != functionParams.getParams() && !functionParams.getParams().isEmpty()) {
            for (AST params : functionParams.getParams()) {
                Variable var1 = (Variable) params;
                BuildInTypeSymbol buildInTypeSymbol = (BuildInTypeSymbol) this.currentScope.lookup("object");
                Symbol symbol1 = new VarSymbol(var1.getIdentifier().getValue(), buildInTypeSymbol);
                this.currentScope.insert(symbol1);
                visit(params);
            }
        }
        visit(functionDeclare.getFunctionParams());
        visit(functionDeclare.getFunctionBody());
        this.currentScope = this.currentScope.getParent();
    }

    private void visitFunctionInvoke(AST ast) {
        FunctionInvoke functionInvoke = (FunctionInvoke) ast;
        Variable variable = (Variable) functionInvoke.getVariable();
        if (!this.currentScope.exists(variable.getIdentifier().getValue())) {
            throw Exceptions.throwIllegalStateException("方法名:" + variable.getIdentifier().getValue() + "不存在");
        }
        visit(functionInvoke.getVariable());
        visit(functionInvoke.getFunctionParams());
    }

    private void visitFunctionName(AST ast) {
        FunctionName functionName = (FunctionName) ast;
        visit(functionName.getVariable());
    }

    private void visitFunctionParams(AST ast) {
        FunctionParams functionParams = (FunctionParams) ast;
        if (null != functionParams.getParams() && !functionParams.getParams().isEmpty()) {
            for (AST params : functionParams.getParams()) {
                visit(params);
            }
        }
    }

    private void visitFunctionReturn(AST ast) {
        FunctionReturn functionReturn = (FunctionReturn) ast;
        AST ast1 = functionReturn.getVariable();
        visit(ast1);
    }

    private void visitIfStatement(AST ast) {
        IfStatement ifStatement = (IfStatement) ast;
        visit(ifStatement.getBoolExpr());
        visit(ifStatement.getStatementBody());
        Token token1 = ifStatement.getKeyword1();
        Token token2 = ifStatement.getKeyword2();
    }

    private void visitProgram(AST ast) {
        Program program = (Program) ast;
        this.currentScope = new SymbolTable(null);
        visit(program.getStatementList());
    }

    private void visitStatementBody(AST ast) {
        StatementBody statementBody = (StatementBody) ast;
        this.currentScope = new SymbolTable(this.currentScope);
        visit(statementBody.getStatementList());
        this.currentScope = this.currentScope.getParent();
    }

    private void visitStatementList(AST ast) {
        StatementList statementList = (StatementList) ast;
        for (AST ast1 : statementList.getStatements()) {
            visit(ast1);
        }
    }

    private void visitTerm(AST ast) {
        Term term = (Term) ast;
        visit(term.getLeftUnaryFactor());
        visit(term.getRightUnaryFactor());
        Token token = term.getOp();
    }

    private void visitUnaryFactor(AST ast) {
        UnaryFactor unaryFactor = (UnaryFactor) ast;
        visit(unaryFactor.getFactor());
        List<Token> tokens = unaryFactor.getMinusOrPlus();
    }

    private void visitVariable(AST ast) {
        Variable variable = (Variable) ast;
        Token token = variable.getIdentifier();
        if (!this.currentScope.exists(token.getValue())) {
            throw Exceptions.throwIllegalStateException("变量:" + token.getValue() + "未定义");
        }
    }

    private void visitVariableDeclare(AST ast) {
        VariableDeclare variableDeclare = (VariableDeclare) ast;
        for (AST ast1 : variableDeclare.getVariables()) {
            Variable variable = (Variable) ast1;
            if (this.currentScope.exists(variable.getIdentifier().getValue())) {
                throw Exceptions.throwIllegalStateException("变量:" + variable.getIdentifier().getValue() + "已存在");
            }
            BuildInTypeSymbol typeSymbol = (BuildInTypeSymbol) this.currentScope.lookup("object");
            Symbol symbol = new VarSymbol(variable.getIdentifier().getValue(), typeSymbol);
            this.currentScope.insert(symbol);
            visit(ast1);
        }
        Token token = variableDeclare.getVarOrLet();
    }
}
