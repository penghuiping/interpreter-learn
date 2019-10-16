package com.php25.interpreterlearn;

import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.engine.Grammar;
import com.php25.interpreterlearn.lexer.Lexer;
import com.php25.interpreterlearn.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/14 15:51
 */
@Slf4j
public class GrammarTest {

    @Test
    public void validateExpression() {
        String value = "(9+125*88),10*(2-10)/2";
        List<Token> tokenList = Lexer.parse(value);


        for (int i = 0; i < tokenList.size(); ) {
            List<Token> tokenList1 = Grammar.validateExpression(tokenList, i);
            if (null != tokenList1) {
                log.info(JsonUtil.toPrettyJson(tokenList1));
                i = i + tokenList1.size();
            } else {
                ++i;
            }


        }

    }
}
