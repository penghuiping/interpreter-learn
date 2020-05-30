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
import com.php25.interpreter.lexer.Tokens;

import java.util.ArrayList;
import java.util.List;

/**
 * variable -> identifier
 * variable_declare -> (var|let) variable(,variable)*
 * assign_statement -> (variable_declare|variable) assign (expr|function_invoke) SEMI
 * <p>
 * basic_type  -> integer|string
 * factor -> basic_type | variable | LeftParenthesis expr RightParenthesis
 * <p>
 * unary_factor -> (PLUS | MINUS)* factor
 * term : unary_factor ((MUL | DIV | MOD) unary_factor)*
 * expr->term ((PLUS | MINUS) term)*
 * <p>
 * function_declare-> function_name function_params function_body
 * function_name -> function variable
 * function_params -> LeftParenthesis (variable (,variable)*)? RightParenthesis
 * function_body -> {statement_list (return variable)?}
 * function_return -> return variable SEMI
 * function_invoke-> variable function_params SEMI
 * <p>
 * statement_list->(assign_statement|function_invoke|function_declare)+
 * program  -> statement_list
 *
 * @author penghuiping
 * @date 2019/10/16 09:40
 */
public class SyntaxParser {

    private List<Token> tokens;

    private int current = 0;

    public SyntaxParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * variable -> identifier
     *
     * @return
     */
    public AST variable() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isIdentifier(token)) {
            this.eat();
            node = new Variable(token);
        }
        return node;
    }


    /**
     * variable_declare -> (var|let) variable(,variable)*
     */
    public AST variableDeclare() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isLet(token) || Tokens.isVar(token)) {
            this.eat();
            AST var = variable();
            if (null != var) {
                List<Variable> variables = new ArrayList<>();
                variables.add((Variable) var);
                while (true) {
                    Token token1 = getCurrentToken();
                    if (Tokens.isComma(token1)) {
                        this.eat();
                        AST var1 = variable();
                        if (var1 != null) {
                            variables.add((Variable) var1);
                        } else {
                            error();
                        }
                    } else {
                        break;
                    }
                }
                node = new VariableDeclare(token, variables);
            }
        }
        return node;
    }


    /**
     * assign_statement -> (variable_declare|variable) assign (expr|function_invoke) SEMI
     */
    public AST assignStatement() {
        AST node = null;
        node = this.variableDeclare();
        if (null != node) {
            Token token = getCurrentToken();
            if (Tokens.isAssign(token)) {
                this.eat();
                AST expr = this.expr();
                if (null != expr) {
                    node = new AssignStatement(node, token, expr);
                    Token token1 = getCurrentToken();
                    if (Tokens.isSemicolon(token1)) {
                        this.eat();
                        return node;
                    } else {
                        error();
                    }
                }

                AST functionInvoke = this.functionInvoke();
                if (null != functionInvoke) {
                    node = new AssignStatement(node, token, functionInvoke);
                    Token token1 = getCurrentToken();
                    if (Tokens.isSemicolon(token1)) {
                        this.eat();
                        return node;
                    } else {
                        error();
                    }
                }
            }
        }

        //可能需要回溯
        Integer index = getCurrentIndex();
        node = this.variable();
        if (null != node) {
            Token token = getCurrentToken();
            if (Tokens.isAssign(token)) {
                this.eat();
                AST expr = this.expr();
                if (null != expr) {
                    node = new AssignStatement(node, token, expr);
                    Token token1 = getCurrentToken();
                    if (Tokens.isSemicolon(token1)) {
                        this.eat();
                        return node;
                    } else {
                        error();
                    }
                }

                AST functionInvoke = this.functionInvoke();
                if (null != functionInvoke) {
                    node = new AssignStatement(node, token, functionInvoke);
                    Token token1 = getCurrentToken();
                    if (Tokens.isSemicolon(token1)) {
                        this.eat();
                        return node;
                    } else {
                        error();
                    }
                }
            } else {
                //进行回溯
                resetCurrentIndex(index);
                return null;
            }
        }
        return node;
    }

    /**
     * basic_type  -> integer|string
     *
     * @return
     */
    public AST basicType() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isInteger(token)) {
            this.eat();
            node = new BasicType(token);
        } else if (Tokens.isString(token)) {
            this.eat();
            node = new BasicType(token);
        }
        return node;
    }

    /**
     * factor -> basic_type | variable | LeftParenthesis expr RightParenthesis
     */
    public AST factor() {
        AST node = null;
        Token token = getCurrentToken();

        node = this.basicType();
        if (node != null) {
            //basic_type
            return new Factor(node);
        }

        node = this.variable();
        if (node != null) {
            //variable
            return new Factor(node);
        }

        if (Tokens.isLeftBracket(token)) {
            this.eat();
            node = this.expr();
            this.eat();
            node = new Factor(node);
        }
        return node;
    }

    /**
     * unary_factor -> (PLUS | MINUS)* factor
     *
     * @return
     */
    public AST unaryFactor() {
        AST node = null;
        List<Token> minusOrPlus = new ArrayList<>();
        while (true) {
            Token token = getCurrentToken();
            if (Tokens.isPlus(token) || Tokens.isMinus(token)) {
                minusOrPlus.add(token);
                this.eat();
            } else {
                AST node1 = this.factor();
                if (null != node1) {
                    node = new UnaryFactor(minusOrPlus, node1);
                    break;
                } else {
                    error();
                }
            }
        }
        return node;
    }


    /**
     * term : unary_factor ((MUL | DIV | MOD) unary_factor)*
     */
    public AST term() {
        AST node = this.unaryFactor();
        if (null != node) {
            Token token = getCurrentToken();

            //注意这里是用while 应为((MUL | DIV) factor)*表没有或者多个
            while (Tokens.isMul(token)
                    || Tokens.isDiv(token)
                    || Tokens.isMod(token)
            ) {
                this.eat();
                AST node1 = this.unaryFactor();
                if (null != node1) {
                    node = new Term(node, token, node1);
                } else {
                    error();
                }
                token = getCurrentToken();
            }
            return node;
        }
        return node;
    }

    /**
     * expr->term ((PLUS | MINUS) term)*
     */
    public AST expr() {
        AST node = term();
        if (node != null) {
            Token token = getCurrentToken();
            //注意这里是用while 应为((PLUS | MINUS) term)*代表没有或者多个
            while (Tokens.isPlus(token) || Tokens.isMinus(token)
            ) {
                this.eat();
                AST node1 = this.term();
                if (null != node1) {
                    node = new Expr(node, token, node1);
                } else {
                    error();
                }
                token = getCurrentToken();
            }
        }
        return node;
    }

    /**
     * function_declare-> function_name function_params function_body
     *
     * @return
     */
    public AST functionDeclare() {
        AST functionName = this.functionName();
        if (null == functionName) {
            return null;
        }

        AST functionParams = this.functionParams();
        if (null == functionParams) {
            return null;
        }

        AST functionBody = this.functionBody();
        if (null == functionBody) {
            return null;
        }
        return new FunctionDeclare(functionName, functionParams, functionBody);
    }

    /**
     * function_name -> function variable
     *
     * @return
     */
    public AST functionName() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isFunction(token)) {
            this.eat();
            node = new FunctionName(this.variable());
        }
        return node;
    }

    /**
     * function_params -> LeftParenthesis variable? (,variable)* RightParenthesis
     *
     * @return
     */
    public AST functionParams() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isLeftBracket(token)) {
            this.eat();
            node = this.variable();
            if (null != node) {
                List<AST> variables = new ArrayList<>();
                variables.add(node);
                token = getCurrentToken();
                while (Tokens.isComma(token)) {
                    this.eat();
                    node = this.variable();
                    if (null != node) {
                        variables.add(node);
                    } else {
                        error();
                    }
                    token = getCurrentToken();
                }
                if (Tokens.isRightBracket(token)) {
                    this.eat();
                } else {
                    error();
                }
                node = new FunctionParams(variables);
            } else {
                this.eat();
                node = new FunctionParams(null);
            }
        }
        return node;
    }


    /**
     * function_body -> {statement_list (return variable)?}
     *
     * @return
     */
    public AST functionBody() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isBigLeftBracket(token)) {
            this.eat();
            node = this.statementList();
            Token token1 = getCurrentToken();
            if (Tokens.isReturn(token1)) {
                AST returnValue = this.functionReturn();
                node = new FunctionBody(node, returnValue);
                Token token2 = getCurrentToken();
                if (Tokens.isBigRightBracket(token2)) {
                    this.eat();
                } else {
                    error();
                }
                return node;
            } else {
                node = new FunctionBody(node, null);
                Token token2 = getCurrentToken();
                if (Tokens.isBigRightBracket(token2)) {
                    this.eat();
                } else {
                    error();
                }
                return node;
            }
        }
        return node;
    }

    /**
     * todo
     * function_invoke-> variable function_params SEMI
     *
     * @return
     */
    public AST functionInvoke() {
        AST node = null;
        node = this.variable();
        if (node != null) {
            AST node1 = this.functionParams();
            if (node1 != null) {
                node = new FunctionInvoke(node, node1);
                Token token = getCurrentToken();
                if (Tokens.isSemicolon(token)) {
                    this.eat();
                } else {
                    error();
                }
            }
        }
        return node;
    }

    /**
     * function_return -> return variable SEMI
     *
     * @return
     */
    public AST functionReturn() {
        AST node = null;
        Token token = getCurrentToken();
        if (Tokens.isReturn(token)) {
            this.eat();
            node = this.variable();
            if (null != node) {
                node = new FunctionReturn(node);
                Token token1 = getCurrentToken();
                if (Tokens.isSemicolon(token1)) {
                    this.eat();
                } else {
                    error();
                }
            }
        }
        return node;
    }


    /**
     * statement_list->(assign_statement|function_invoke|function_declare)+
     */
    public AST statementList() {
        AST node = null;
        //存在，加入statement 并且继续判断
        List<AST> list = new ArrayList<>();
        StatementList resultNode = new StatementList(list);
        while (true) {
            //尝试assign_statement
            node = this.assignStatement();
            if (null != node) {
                //存在，加入statement 并且继续判断
                list.add(node);
                continue;
            }

            //尝试function_declare
            node = this.functionDeclare();
            if (null != node) {
                //存在，加入statement 并且继续判断
                list.add(node);
                continue;
            }

            //尝试function_invoke
            node = this.functionInvoke();
            if (null != node) {
                //存在，加入statement 并且继续判断
                list.add(node);
                continue;
            }

            if (node == null) {
                break;
            }
        }
        return resultNode;
    }

    /**
     * program  -> statement_list
     *
     * @return
     */
    public AST program() {
        AST node = this.statementList();
        if (null != node) {
            node = new Program(node);
        }
        return node;
    }

    public AST parse() {
        AST ast = this.program();
        Token token = getCurrentToken();
        if (!Tokens.isEOF(token)) {
            error();
        }
        return ast;
    }


    private void error() {
        throw Exceptions.throwIllegalStateException("syntax error");
    }

    private void eat() {
        current = current + 1;
    }


    private Token getNextToken() {
        if ((current + 1) < tokens.size()) {
            return tokens.get(current + 1);
        } else {
            return null;
        }
    }

    private Token getCurrentToken() {
        if (current < tokens.size()) {
            return tokens.get(current);
        }
        return null;
    }

    private Integer getCurrentIndex() {
        return current;
    }

    private void resetCurrentIndex(Integer index) {
        this.current = index;
    }
}
