package com.php25.interpreterlearn;

import com.php25.interpreterlearn.ast.AST;
import com.php25.interpreterlearn.ast.ASTParser;
import com.php25.interpreterlearn.ast.Asts;
import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.engine.Grammar;
import com.php25.interpreterlearn.runtime.Interpreter;
import com.php25.interpreterlearn.lexer.Lexer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class CompilerLearnApplicationTests {


    @Test
    public void sample1Test() {
        String value = "(9+125*8)+10*(2-10/2)*10";
        List<Token> tokens = Lexer.parse(value);

        //判断是否符合表达式语句语法
        for (int i = 0; i < tokens.size(); i++) {
            List<Token> tokensGrammar = Grammar.validateExpression(tokens, i);
            if (null != tokensGrammar) {
                ASTParser newParser = new ASTParser(tokensGrammar);
                AST ast = newParser.parse();
                Asts.middleOrderTraversalTree(ast, token -> {
                    System.out.print(token.getValue());
                });
                System.out.println();
                Interpreter interpreter = new Interpreter();
                log.info("" + interpreter.visit(ast));
                i = i + tokensGrammar.size();
            }
        }
    }

}
