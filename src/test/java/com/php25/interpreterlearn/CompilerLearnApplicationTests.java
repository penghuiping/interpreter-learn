package com.php25.interpreterlearn;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.ast.ASTParser;
import com.php25.interpreterlearn.ast.Asts;
import com.php25.interpreterlearn.engine.Grammar;
import com.php25.interpreterlearn.lexer.Lexer;
import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.runtime.Interpreter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Slf4j
public class CompilerLearnApplicationTests {


    @Test
    public void sample0Test() {
        String value = "(3*3/3+3-3)%3";
        List<Token> tokens = Lexer.parse(value);
        List<Token> tokensGrammar = Grammar.validateExpression(tokens, 0);
        if (null != tokensGrammar) {
            ASTParser newParser = new ASTParser(tokensGrammar);
            AST ast = newParser.parse();
            Asts.middleOrderTraversalTree(ast, token -> {
                System.out.print(token.getValue());
            });
            System.out.println();
            Interpreter interpreter = new Interpreter();
            Integer result = interpreter.visit(ast);
            Assert.assertSame(result,0);
        }
    }


    @Test
    public void sample1Test() {
        String value = "3*(-3)*(-3)*3";
        List<Token> tokens = Lexer.parse(value);
        List<Token> tokensGrammar = Grammar.validateExpression(tokens, 0);
        if (null != tokensGrammar) {
            ASTParser newParser = new ASTParser(tokensGrammar);
            AST ast = newParser.parse();
            Asts.middleOrderTraversalTree(ast, token -> {
                System.out.print(token.getValue());
            });
            System.out.println();
            Interpreter interpreter = new Interpreter();
            Integer result = interpreter.visit(ast);
            Assert.assertSame(result,81);
        }
    }



}
