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
        String value =
                "var a= (3*3/3+3-3)%3;"
                        + "let b=a+1;";
        List<Token> tokens = Lexer.parse(value);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        System.out.println();
    }
}
