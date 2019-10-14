package com.php25.interpreterlearn;

import com.php25.interpreterlearn.bo.ExpressionNode;
import com.php25.interpreterlearn.bo.Token;
import com.php25.interpreterlearn.engine.AST;
import com.php25.interpreterlearn.engine.Core;
import com.php25.interpreterlearn.engine.Grammar;
import com.php25.interpreterlearn.engine.Lexer;
import com.php25.interpreterlearn.engine.Parser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompilerLearnApplicationTests {


    @Test
    public void sampleTest() {
        String value = "9+125*8+10*(2-10)/2";
        List<Token> tokens = Lexer.parse(value);

        //判断是否符合表达式语句语法
        for (int i = 0; i < tokens.size(); i++) {
            List<Token> tokensGrammar = Grammar.validateExpression(tokens, i);
            if (null != tokensGrammar) {
                //符合表达式语句语法
                //把token转化为AST
                ExpressionNode treeNode = AST.expression(tokensGrammar);

                //解释执行AST
                log.info("" + Parser.expression(treeNode));

                i = i + tokensGrammar.size();
            }
        }
    }
}
