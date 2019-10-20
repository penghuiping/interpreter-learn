package com.php25.interpreterlearn.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/18 13:55
 */
@Slf4j
public class RegexTest {

    @Test
    public void test() {
        String expr = "(0|1|2|3|4|5|6|7|8|9|0)*";
        List<Token> tokenList = Lexer.parse(expr);
        System.out.println();
        Parser parser = new Parser(tokenList);
        AstRegex ast = parser.start();
        parser.visitor(ast);
        Nfa nfa = NfaUtil.constructFrom(ast);
        System.out.println();
        String text = "你好你好你nnn678好好宁浩你哈322就是这么回事123哈哈哈haohoaofhaof";
        System.out.println("需要匹配的字符串为：" + text);
        List<MatchedText> rs = NfaUtil.search(text, nfa);
        System.out.println("" + rs);
    }


}
