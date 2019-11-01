package com.php25.regex;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2019/10/18 21:50
 */
@Getter
@Setter
class Nfa {

    private NfaState start;

    private NfaState end;


    public Nfa(NfaState start, NfaState end) {
        this.start = start;
        this.end = end;
    }
}
