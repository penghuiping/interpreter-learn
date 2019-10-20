package com.php25.interpreterlearn.regex;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A state in Thompson's NFA can either have
 * - a single symbol transition to a state
 * or
 * - up to two epsilon transitions to another states
 * but not both.
 *
 * @author penghuiping
 * @date 2019/10/18 21:52
 */
@Setter
@Getter
class NfaState {

    boolean isEnd;

    List<NfaState> epsilonTransitions = new ArrayList<>();

    Map<String, NfaState> transition = new HashMap<>();

    public NfaState(boolean isEnd) {
        this.isEnd = isEnd;
    }
}
