package com.php25.interpreter;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.ast.Asts;
import com.php25.interpreter.syntax.SyntaxParser;
import com.php25.interpreter.engine.GlobalMemory;
import com.php25.interpreter.lexer.Lexer;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.interpreter.Interpreter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Slf4j
public class InterpreterTest {


    @Test
    public void sample0Test() {
        String value = "var a= (3*3/3+3-3)%3";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.middleOrderTraversalTree(ast, token -> {
            System.out.print(token.getValue());
        });
        System.out.println();
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.visit(ast);
        Assert.assertSame(result, 0);
    }


    @Test
    public void sample1Test() {
        String value = "3*(-3)*(-3)*3";
        List<Token> tokens = Lexer.parse(value);

        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.middleOrderTraversalTree(ast, token -> {
            System.out.print(token.getValue());
        });
        System.out.println();
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.visit(ast);
        Assert.assertSame(result, 81);
    }


    @Test
    public void sample2Test() {
        String value = "{" +
                "a=(3+3)*3/3%6;" +
                "{" +
                "b=(3+3)*3/3%6+1;" +
                "c=3" +
                "};" +
                "d=(-5)" +
                "}";
        List<Token> tokens = Lexer.parse(value);

        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
//        Asts.middleOrderTraversalTree(ast, token -> {
//            System.out.print(token.getValue());
//        });
        System.out.println();
        Interpreter interpreter = new Interpreter();
        interpreter.visit(ast);
        log.info("内存中内容为:\n{}", GlobalMemory.getPrintContent());
    }


}
