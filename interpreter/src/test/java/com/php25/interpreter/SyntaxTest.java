package com.php25.interpreter;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.ast.Asts;
import com.php25.interpreter.lexer.Lexer;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.ast.SyntaxParser;
import org.junit.Test;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/11/4 22:03
 */
public class SyntaxTest {


    @Test
    public void test1() {
        String value = "a=+-+---b;";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.printAST(ast);
    }

    @Test
    public void test2() {
        String value = "var a=1+b/(--b);";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.printAST(ast);
    }

    @Test
    public void test3() {
        String value = "let a=1+b-(--b);";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.printAST(ast);
    }

    @Test
    public void test4() {
        String value0 = "a=+-+---b;\n";
        String value1 = "var a=1+b/(--b);\n";
        String value2 = "let a=1+b-(--b);\n";

        String value4 = value0 + value1 + value2;
        List<Token> tokens = Lexer.parse(value4);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.printAST(ast);
    }

    @Test
    public void test5() {
        String value0 = "function print(){ a=1;}";
        List<Token> tokens = Lexer.parse(value0);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.printAST(ast);
    }

    @Test
    public void test6() {
        String value0 =
                "function add(a,b){" +
                        "a=a+b; " +
                        "a=a+1; " +
                        "return a;" +
                        "}";
        String value1 = "a=+-+---b;\n";
        String value2 = value0 + value1;
        List<Token> tokens = Lexer.parse(value2);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();

        Asts.printAST(ast);
    }

    @Test
    public void test7() {
        String value0 = "a=1;";
        String value1 = "print(1);";
        List<Token> tokens = Lexer.parse(value0 + value1);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.printAST(ast);
    }

    @Test
    public void test8() {
        String value0 =
                "if(a>0){" +
                    "print(\"大于0\");" +
                "}else if(a==0) {" +
                    "print(\"等于0\");" +
                "}else {" +
                    "print(\"小于0\");" +
                "}";
        List<Token> tokens = Lexer.parse(value0 );
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        Asts.printAST(ast);
    }
}
