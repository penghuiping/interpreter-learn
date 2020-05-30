package com.php25.interpreter.ast;

import com.php25.exception.Exceptions;
import com.php25.interpreter.ast.sub.AssignStatement;
import com.php25.interpreter.ast.sub.BasicType;
import com.php25.interpreter.ast.sub.Expr;
import com.php25.interpreter.ast.sub.Factor;
import com.php25.interpreter.ast.sub.FunctionBody;
import com.php25.interpreter.ast.sub.FunctionDeclare;
import com.php25.interpreter.ast.sub.FunctionInvoke;
import com.php25.interpreter.ast.sub.FunctionName;
import com.php25.interpreter.ast.sub.FunctionParams;
import com.php25.interpreter.ast.sub.FunctionReturn;
import com.php25.interpreter.ast.sub.Program;
import com.php25.interpreter.ast.sub.StatementList;
import com.php25.interpreter.ast.sub.Term;
import com.php25.interpreter.ast.sub.UnaryFactor;
import com.php25.interpreter.ast.sub.Variable;
import com.php25.interpreter.ast.sub.VariableDeclare;
import com.php25.interpreter.lexer.Token;
import com.php25.util.StringUtil;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/16 11:25
 */
public class Asts {


    public static void printAST(AST ast) {
        printAST0(ast, 0);
    }

    private static void printAST0(AST ast, int deep) {
        printSpace(deep);
        if (ast instanceof Program) {
            System.out.println("Program");
            Program program = (Program) ast;
            AST statementList = program.getStatementList();
            printAST0(statementList, deep + 1);
        } else if (ast instanceof StatementList) {
            System.out.println("StatementList");
            StatementList statementList = (StatementList) ast;
            for (AST statement : statementList.getStatements()) {
                printAST0(statement, deep + 1);
            }
        } else if (ast instanceof AssignStatement) {
            System.out.println("AssignStatement");
            AssignStatement assignStatement = (AssignStatement) ast;
            AST var = assignStatement.getVariable();
            printAST0(var, deep + 1);
            printValue(assignStatement.getAssign().getValue(), deep + 1);
            AST expr = assignStatement.getExpr();
            printAST0(expr, deep + 1);
        } else if (ast instanceof BasicType) {
            BasicType expr = (BasicType) ast;
            System.out.println(expr.getToken().getValue());
        } else if (ast instanceof Expr) {
            System.out.println("Expr");
            Expr expr = (Expr) ast;
            printAST0(expr.getLeftTerm(), deep + 1);
            printValue(expr.getOp().getValue(), deep + 1);
            printAST0(expr.getRightTerm(), deep + 1);
        } else if (ast instanceof Factor) {
            System.out.println("Factor");
            Factor expr = (Factor) ast;
            printAST0(expr.getAst(), deep + 1);
        } else if (ast instanceof FunctionBody) {
            System.out.println("FunctionBody");
            FunctionBody expr = (FunctionBody) ast;
            if(null != expr.getReturnValue()) {
                printAST0(expr.getReturnValue(), deep + 1);
            }
            printAST0(expr.getStatementList(), deep + 1);
        } else if (ast instanceof FunctionDeclare) {
            System.out.println("FunctionDeclare");
            FunctionDeclare expr = (FunctionDeclare) ast;
            printAST0(expr.getFunctionName(), deep + 1);
            printAST0(expr.getFunctionBody(), deep + 1);
            printAST0(expr.getFunctionParams(), deep + 1);
        } else if (ast instanceof FunctionInvoke) {
            System.out.println("FunctionInvoke");
            FunctionInvoke expr = (FunctionInvoke) ast;
            printAST0(expr.getVariable(), deep + 1);
            printAST0(expr.getFunctionParams(), deep + 1);
        } else if (ast instanceof FunctionName) {
            System.out.println("FunctionName");
            FunctionName expr = (FunctionName) ast;
            printAST0(expr.getVariable(), deep + 1);
        } else if (ast instanceof FunctionParams) {
            System.out.println("FunctionParams");
            FunctionParams expr = (FunctionParams) ast;
            if(expr.getVariables()!=null && expr.getVariables().size()>0) {
                for (AST variable : expr.getVariables()) {
                    printAST0(variable, deep + 1);
                }
            }
        } else if (ast instanceof FunctionReturn) {
            System.out.println("FunctionReturn");
            FunctionReturn expr = (FunctionReturn) ast;
            printAST0(expr.getVariable(), deep + 1);
        } else if (ast instanceof Term) {
            System.out.println("Term");
            Term expr = (Term) ast;
            printAST0(expr.getLeftUnaryFactor(), deep + 1);
            printValue(expr.getOp().getValue(), deep + 1);
            printAST0(expr.getRightUnaryFactor(), deep + 1);
        } else if (ast instanceof UnaryFactor) {
            System.out.println("UnaryFactor");
            UnaryFactor expr = (UnaryFactor) ast;
            printAST0(expr.getFactor(), deep + 1);
            StringBuilder sb = new StringBuilder();
            for (Token minusOrPlus : expr.getMinusOrPlus()) {
                sb.append(minusOrPlus.getValue());
            }
            if(!StringUtil.isBlank(sb.toString())) {
                printValue(sb.toString(), deep + 1);
            }
        } else if (ast instanceof Variable) {
            Variable expr = (Variable) ast;
            System.out.println(expr.getIdentifier().getValue());
        } else if (ast instanceof VariableDeclare) {
            System.out.println("VariableDeclare");
            VariableDeclare expr = (VariableDeclare) ast;
            List<Variable> variables = expr.getVariables();
            for (AST var : variables) {
                printAST0(var, deep + 1);
            }
            printValue(expr.getVarOrLet().getValue(), deep + 1);
        } else {
            throw Exceptions.throwImpossibleException();
        }
    }

    private static void printSpace(int deep) {
        for (int i = 0; i < deep; i++) {
            System.out.print("\t");
        }
    }

    private static void printValue(String value, int deep) {
        printSpace(deep);
        System.out.print(value + "\n");
    }

    public static void error() {
        throw Exceptions.throwIllegalStateException("syntax error");
    }
}
