package com.php25.interpreterlearn.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/20 16:52
 */
@Setter
@Getter
@AllArgsConstructor
public class MatchedText {

    private String text;

    private int start;

    private int end;

    @Override
    public String toString() {
        return String.format("{%s}", text);
    }
}
