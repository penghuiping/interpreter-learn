package com.php25.interpreterlearn;

import com.php25.interpreterlearn.lexer.Token;
import com.php25.interpreterlearn.lexer.Lexer;
import com.php25.interpreterlearn.util.JsonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CompilerLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompilerLearnApplication.class, args);

        List<Token> tokens = Lexer.parse("11+11230+231");
        System.out.println(JsonUtil.toPrettyJson(tokens));
    }

}
