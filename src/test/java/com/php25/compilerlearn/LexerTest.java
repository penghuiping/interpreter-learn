package com.php25.compilerlearn;

import com.php25.compilerlearn.bo.Token;
import com.php25.compilerlearn.bo.ExpressionNode;
import com.php25.compilerlearn.constant.TokenType;
import com.php25.compilerlearn.engine.AST;
import com.php25.compilerlearn.engine.Core;
import com.php25.compilerlearn.engine.Grammar;
import com.php25.compilerlearn.engine.Lexer;
import com.php25.compilerlearn.engine.Parser;
import com.php25.compilerlearn.exception.IllegalStateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author penghuiping
 * @date 2019/10/10 09:31
 */
@Slf4j
public class LexerTest {
    @Test
    public void digit() {
        //反例
        boolean thrown = false;
        try {
            Lexer.parse("1231abc");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        Assert.assertTrue(thrown);

        //正例
        List<Token> tokenList = Lexer.parse("1231");
        Assert.assertSame(tokenList.get(0).getType(), TokenType.digit);
    }

    @Test
    public void label() {
        //正例
        List<Token> tokenList = Lexer.parse("a1231abA");
        Assert.assertSame(tokenList.get(0).getType(), TokenType.label);

        List<Token> tokenList1 = Lexer.parse("_123A1B_");
        Assert.assertSame(tokenList1.get(0).getType(), TokenType.label);

        //反例
        boolean thrown = false;
        try {
            Lexer.parse("a%a$aa");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        Assert.assertTrue(thrown);
    }


    @Test
    public void bool() {
        //正例
        List<Token> tokenList = Lexer.parse("true");
        Assert.assertSame(tokenList.get(0).getType(), TokenType.bool);

        List<Token> tokenList1 = Lexer.parse("false");
        Assert.assertSame(tokenList1.get(0).getType(), TokenType.bool);

        //反例
        List<Token> tokenList2 = Lexer.parse("true123");
        Assert.assertSame(tokenList2.get(0).getType(), TokenType.label);

        List<Token> tokenList3 = Lexer.parse("false123");
        Assert.assertSame(tokenList3.get(0).getType(), TokenType.label);
    }


    @Test
    public void string() {
        //正例
        List<Token> tokenList = Lexer.parse("\"hello world\"\n\"hello world!\"");
        Assert.assertSame(tokenList.get(0).getType(), TokenType.string);
        Assert.assertSame(tokenList.get(1).getType(), TokenType.string);
    }


    @Test
    public void operator() {
        List<Token> tokenList = Lexer.parse("1231+123-111*12/12%3");
        Assert.assertSame(tokenList.get(0).getType(), TokenType.digit);
        Assert.assertSame(tokenList.get(1).getType(), TokenType.operator);
        Assert.assertSame(tokenList.get(2).getType(), TokenType.digit);
        Assert.assertSame(tokenList.get(3).getType(), TokenType.operator);
        Assert.assertSame(tokenList.get(4).getType(), TokenType.digit);
        Assert.assertSame(tokenList.get(5).getType(), TokenType.operator);
        Assert.assertSame(tokenList.get(6).getType(), TokenType.digit);
        Assert.assertSame(tokenList.get(7).getType(), TokenType.operator);
        Assert.assertSame(tokenList.get(8).getType(), TokenType.digit);
        Assert.assertSame(tokenList.get(9).getType(), TokenType.operator);
        Assert.assertSame(tokenList.get(10).getType(), TokenType.digit);

        List<Token> tokenList1 = Lexer.parse("11==11");
        Assert.assertSame(tokenList1.get(0).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(1).getType(), TokenType.operator);
        Assert.assertEquals(tokenList1.get(1).getValue(), "==");
        Assert.assertSame(tokenList1.get(2).getType(), TokenType.digit);

        List<Token> tokenList2 = Lexer.parse("false&&true");
        Assert.assertSame(tokenList2.get(0).getType(), TokenType.bool);
        Assert.assertSame(tokenList2.get(1).getType(), TokenType.operator);
        Assert.assertSame(tokenList2.get(2).getType(), TokenType.bool);


        List<Token> tokenList3 = Lexer.parse("false||true");
        Assert.assertSame(tokenList3.get(0).getType(), TokenType.bool);
        Assert.assertSame(tokenList3.get(1).getType(), TokenType.operator);
        Assert.assertSame(tokenList3.get(2).getType(), TokenType.bool);

        List<Token> tokenList4 = Lexer.parse("a=1");
        Assert.assertSame(tokenList4.get(0).getType(), TokenType.label);
        Assert.assertSame(tokenList4.get(1).getType(), TokenType.operator);
        Assert.assertEquals(tokenList4.get(1).getValue(), "=");
        Assert.assertSame(tokenList4.get(2).getType(), TokenType.digit);

        List<Token> tokenList5 = Lexer.parse("a=\"name\"");
        Assert.assertSame(tokenList5.get(0).getType(), TokenType.label);
        Assert.assertSame(tokenList5.get(1).getType(), TokenType.operator);
        Assert.assertEquals(tokenList5.get(1).getValue(), "=");
        Assert.assertSame(tokenList5.get(2).getType(), TokenType.string);
    }

    @Test
    public void separator() {
        List<Token> tokenList1 = Lexer.parse("  11 ==  11; 22  ==  11   ;\n\n11 ==  11;");
        Assert.assertSame(tokenList1.get(0).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(1).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(2).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(3).getType(), TokenType.operator);
        Assert.assertSame(tokenList1.get(4).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(5).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(6).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(7).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(8).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(9).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(10).getType(), TokenType.operator);
        Assert.assertSame(tokenList1.get(11).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(12).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(13).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(14).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(15).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(16).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(17).getType(), TokenType.operator);
        Assert.assertSame(tokenList1.get(18).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(19).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(20).getType(), TokenType.separator);
    }

    @Test
    public void keyword() {
        List<Token> tokenList4 = Lexer.parse("var a=1");
        Assert.assertSame(tokenList4.get(0).getType(), TokenType.keyword);
        Assert.assertSame(tokenList4.get(1).getType(), TokenType.separator);
        Assert.assertSame(tokenList4.get(2).getType(), TokenType.label);
        Assert.assertSame(tokenList4.get(3).getType(), TokenType.operator);
        Assert.assertEquals(tokenList4.get(3).getValue(), "=");
        Assert.assertSame(tokenList4.get(4).getType(), TokenType.digit);
    }


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
                log.info(""+ Parser.expression(treeNode));


                i = i + tokensGrammar.size();
            }
        }
    }


    public int execute(String expression) {
        List<Token> tokens = Lexer.parse(expression);
        int v = -1;
        for (int i = 0; i < tokens.size(); i++) {
            //判断是否符合表达式语句语法
            List<Token> tokensGrammar = Grammar.validateExpression(tokens, i);
            if (tokensGrammar != null) {
                //express表达式语法匹配
                v = Core.express(tokensGrammar.get(1).getValue(), Integer.parseInt(tokensGrammar.get(0).getValue()), Integer.parseInt(tokensGrammar.get(2).getValue()));
                i = i + tokensGrammar.size();
            }
            //判断是否符合赋值语句语法
        }
        return v;
    }
}
