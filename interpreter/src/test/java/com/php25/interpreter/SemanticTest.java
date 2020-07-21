package com.php25.interpreter;

import com.php25.interpreter.ast.AST;
import com.php25.interpreter.ast.Asts;
import com.php25.interpreter.ast.SyntaxParser;
import com.php25.interpreter.lexer.Lexer;
import com.php25.interpreter.lexer.Token;
import com.php25.interpreter.sematic.SemanticParser;
import org.junit.Test;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/7/20 16:10
 */
public class SemanticTest {

    @Test
    public void test() {
        String cmd = "var x=1;var y=2+x;" +
                "function print(a) {" +
                "a=2;" +
                "}" +
                "var a=1;";
        List<Token> tokens = Lexer.parse(cmd);
        SyntaxParser newParser = new SyntaxParser(tokens);
        AST ast = newParser.parse();
        System.out.println();
        Asts.printAST(ast);
        SemanticParser semanticParser = new SemanticParser();
        semanticParser.visit(ast);
    }
}
