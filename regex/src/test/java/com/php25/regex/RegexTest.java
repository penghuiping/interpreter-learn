package com.php25.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.swing.*;
import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/18 13:55
 */
@Slf4j
public class RegexTest {

    private String letter = "(a|b|c|d|e|f|g|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)";

    private String digit = "(0|1|2|3|4|5|6|7|8|9)";

    @Test
    public void test() {
        String expr = digit+"*";
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

    @Test
    public void testXml() {
        String expr = "(<" + letter + "*>)|"+"(</" + letter + "*>)";
        List<Token> tokenList = Lexer.parse(expr);
        System.out.println();
        Parser parser = new Parser(tokenList);
        AstRegex ast = parser.start();
        parser.visitor(ast);
        Nfa nfa = NfaUtil.constructFrom(ast);
        System.out.println();
        String text = "<test><name>你好</name><age>12</age></test>";
        System.out.println("需要匹配的字符串为：" + text);
        List<MatchedText> rs = NfaUtil.search(text, nfa);
        System.out.println("" + rs);
    }


}
