package com.php25.interpreterlearn;

import com.php25.interpreterlearn.bo.Token;
import com.php25.interpreterlearn.constant.TokenType;
import com.php25.interpreterlearn.engine.Lexer;
import com.php25.interpreterlearn.exception.IllegalStateException;
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
    public void term() {
        //正例
        List<Token> tokenList = Lexer.parse("a1231abA");
        Assert.assertSame(tokenList.get(0).getType(), TokenType.term);

        List<Token> tokenList1 = Lexer.parse("_123A1B_");
        Assert.assertSame(tokenList1.get(0).getType(), TokenType.term);

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
        Assert.assertSame(tokenList2.get(0).getType(), TokenType.term);

        List<Token> tokenList3 = Lexer.parse("false123");
        Assert.assertSame(tokenList3.get(0).getType(), TokenType.term);
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


    }

    @Test
    public void boolOperator() {
        List<Token> tokenList1 = Lexer.parse("11==11");
        Assert.assertSame(tokenList1.get(0).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(1).getType(), TokenType.boolOperator);
        Assert.assertEquals(tokenList1.get(1).getValue(), "==");
        Assert.assertSame(tokenList1.get(2).getType(), TokenType.digit);

        List<Token> tokenList2 = Lexer.parse("false&&true");
        Assert.assertSame(tokenList2.get(0).getType(), TokenType.bool);
        Assert.assertSame(tokenList2.get(1).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList2.get(2).getType(), TokenType.bool);


        List<Token> tokenList3 = Lexer.parse("false||true");
        Assert.assertSame(tokenList3.get(0).getType(), TokenType.bool);
        Assert.assertSame(tokenList3.get(1).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList3.get(2).getType(), TokenType.bool);

        List<Token> tokenList4 = Lexer.parse("1>2");
        Assert.assertSame(tokenList4.get(0).getType(), TokenType.digit);
        Assert.assertSame(tokenList4.get(1).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList4.get(2).getType(), TokenType.digit);

        List<Token> tokenList5 = Lexer.parse("1>=2");
        Assert.assertSame(tokenList5.get(0).getType(), TokenType.digit);
        Assert.assertSame(tokenList5.get(1).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList5.get(2).getType(), TokenType.digit);

        List<Token> tokenList6 = Lexer.parse("a<b");
        Assert.assertSame(tokenList6.get(0).getType(), TokenType.term);
        Assert.assertSame(tokenList6.get(1).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList6.get(2).getType(), TokenType.term);

        List<Token> tokenList7 = Lexer.parse("a<=b");
        Assert.assertSame(tokenList7.get(0).getType(), TokenType.term);
        Assert.assertSame(tokenList7.get(1).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList7.get(2).getType(), TokenType.term);
    }

    @Test
    public void assign() {
        List<Token> tokenList4 = Lexer.parse("a=1");
        Assert.assertSame(tokenList4.get(0).getType(), TokenType.term);
        Assert.assertSame(tokenList4.get(1).getType(), TokenType.assign);
        Assert.assertEquals(tokenList4.get(1).getValue(), "=");
        Assert.assertSame(tokenList4.get(2).getType(), TokenType.digit);

        List<Token> tokenList5 = Lexer.parse("a=\"name\"");
        Assert.assertSame(tokenList5.get(0).getType(), TokenType.term);
        Assert.assertSame(tokenList5.get(1).getType(), TokenType.assign);
        Assert.assertEquals(tokenList5.get(1).getValue(), "=");
        Assert.assertSame(tokenList5.get(2).getType(), TokenType.string);
    }

    @Test
    public void separator() {
        List<Token> tokenList1 = Lexer.parse("  11 ==  11; 22  ==  11   ;\n\n11 ==  11;");
        Assert.assertSame(tokenList1.get(0).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(1).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList1.get(2).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(3).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(4).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(5).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList1.get(6).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(7).getType(), TokenType.separator);
        Assert.assertSame(tokenList1.get(8).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(9).getType(), TokenType.boolOperator);
        Assert.assertSame(tokenList1.get(10).getType(), TokenType.digit);
        Assert.assertSame(tokenList1.get(11).getType(), TokenType.separator);
    }

    @Test
    public void keywordAndBracket() {
        List<Token> tokenList4 = Lexer.parse("var a=1;if(a>0){}else if(a==0) else {}");
        Assert.assertSame(tokenList4.get(0).getType(), TokenType.keyword); // var
        Assert.assertSame(tokenList4.get(1).getType(), TokenType.term); // a
        Assert.assertSame(tokenList4.get(2).getType(), TokenType.assign);// =
        Assert.assertSame(tokenList4.get(3).getType(), TokenType.digit);// 1
        Assert.assertSame(tokenList4.get(4).getType(), TokenType.separator);//;
        Assert.assertSame(tokenList4.get(5).getType(), TokenType.keyword);// if
        Assert.assertSame(tokenList4.get(6).getType(), TokenType.leftBracket);// (
        Assert.assertSame(tokenList4.get(7).getType(), TokenType.term);// a
        Assert.assertSame(tokenList4.get(8).getType(), TokenType.boolOperator);//>
        Assert.assertSame(tokenList4.get(9).getType(), TokenType.digit);//0
        Assert.assertSame(tokenList4.get(10).getType(), TokenType.rightBracket);//)
        Assert.assertSame(tokenList4.get(11).getType(), TokenType.bigBracket);//{
        Assert.assertSame(tokenList4.get(12).getType(), TokenType.bigBracket);//}
        Assert.assertSame(tokenList4.get(13).getType(), TokenType.keyword);//else
        Assert.assertSame(tokenList4.get(14).getType(), TokenType.keyword);//if
        Assert.assertSame(tokenList4.get(15).getType(), TokenType.leftBracket);//(
        Assert.assertSame(tokenList4.get(16).getType(), TokenType.term);//a
        Assert.assertSame(tokenList4.get(17).getType(), TokenType.boolOperator);//==
        Assert.assertSame(tokenList4.get(18).getType(), TokenType.digit);//0
        Assert.assertSame(tokenList4.get(19).getType(), TokenType.rightBracket);//)
        Assert.assertSame(tokenList4.get(20).getType(), TokenType.keyword);//else
        Assert.assertSame(tokenList4.get(21).getType(), TokenType.bigBracket);//{
        Assert.assertSame(tokenList4.get(22).getType(), TokenType.bigBracket);//{
    }


}