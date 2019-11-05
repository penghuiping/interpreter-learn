package com.php25.interpreter;

import com.php25.interpreter.lexer.Lexer;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.syntax.SyntaxParser;
import org.junit.Test;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/11/4 22:03
 */
public class SyntaxTest {


    @Test
    public void test1() {
        String value = "a=+-+---b";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        System.out.println();
    }

    @Test
    public void test2() {
        String value = "var a=1+b/(--b)";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        System.out.println();
    }

    @Test
    public void test3() {
        String value = "let a=1+b-(--b)";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        System.out.println();
    }

    @Test
    public void test4() {
        String value0 = "a=+-+---b;\n";
        String value1 = "var a=1+b/(--b);\n";
        String value2 = "let a=1+b-(--b);\n";

        String value4 = value0+value1+value2;
        List<Token> tokens = Lexer.parse(value4);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        System.out.println();
    }
}
