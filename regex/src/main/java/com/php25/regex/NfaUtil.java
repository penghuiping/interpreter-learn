package com.php25.regex;


import com.php25.exception.Exceptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author penghuiping
 * @date 2019/10/18 22:10
 */
public class NfaUtil {

    public static void addEpsilonTransition(NfaState from, NfaState to) {
        from.epsilonTransitions.add(to);
    }


    public static void addTransition(NfaState from, NfaState to, String symbol) {
        from.transition.put(symbol, to);
    }

    public static Nfa fromEpsilon() {
        NfaState start = new NfaState(false);
        NfaState end = new NfaState(true);
        addEpsilonTransition(start, end);
        return new Nfa(start, end);
    }


    public static Nfa fromSymbol(String symbol) {
        NfaState start = new NfaState(false);
        NfaState end = new NfaState(true);
        addTransition(start, end, symbol);
        return new Nfa(start, end);
    }


    public static Nfa concat(Nfa first, Nfa second) {
        first.getEnd().isEnd = false;
        second.getEnd().isEnd = true;
        addEpsilonTransition(first.getEnd(), second.getStart());
        return new Nfa(first.getStart(), second.getEnd());
    }


    public static Nfa union(Nfa first, Nfa second) {
        NfaState start = new NfaState(false);
        NfaState end = new NfaState(true);
        first.getEnd().isEnd = false;
        second.getEnd().isEnd = false;

        addEpsilonTransition(start, first.getStart());
        addEpsilonTransition(start, second.getStart());

        addEpsilonTransition(first.getEnd(), end);
        addEpsilonTransition(second.getEnd(), end);

        return new Nfa(start, end);
    }

    public static Nfa closure(Nfa nfa) {
        NfaState start = new NfaState(false);
        NfaState end = new NfaState(true);
        nfa.getEnd().isEnd = false;

        addEpsilonTransition(start, end);
        addEpsilonTransition(start, nfa.getStart());
        addEpsilonTransition(nfa.getEnd(), end);
        addEpsilonTransition(nfa.getEnd(), nfa.getStart());

        return new Nfa(start, end);
    }


    public static Nfa constructFrom(AstRegex ast) {
        if (ast instanceof AstClosure) {
            AstClosure astClosure = (AstClosure) ast;
            Nfa nfa = constructFrom(astClosure.getExpr());
            return closure(nfa);
        } else if (ast instanceof AstConcat) {
            AstConcat astConcat = (AstConcat) ast;
            Nfa nfa1 = constructFrom(astConcat.getLeftExpr());
            Nfa nfa2 = constructFrom(astConcat.getRightExpr());
            return concat(nfa1, nfa2);
        } else if (ast instanceof AstSymbol) {
            AstSymbol astSymbol = (AstSymbol) ast;
            return fromSymbol(astSymbol.getValue().getValue());
        } else if (ast instanceof AstUnion) {
            AstUnion astUnion = (AstUnion) ast;
            Nfa nfa1 = constructFrom(astUnion.getLeftExpr());
            Nfa nfa2 = constructFrom(astUnion.getRightExpr());
            return union(nfa1, nfa2);
        } else {
            throw Exceptions.throwIllegalStateException("无法识别此AstRegex节点");
        }
    }


    public static List<MatchedText> search(String text, Nfa nfa) {
        List<MatchedText> removeList = new ArrayList<>();
        List<MatchedText> matchedTexts = search0(text, nfa);
        if (null != matchedTexts && !matchedTexts.isEmpty()) {
            for (int i = 0; i < matchedTexts.size(); i++) {
                MatchedText matchedText = matchedTexts.get(i);
                for (int j = i+1; j < matchedTexts.size(); j++) {
                    MatchedText tmp = matchedTexts.get(j);
                    if (tmp.getStart() <= matchedText.getStart() && tmp.getEnd() >= matchedText.getEnd()) {
                        removeList.add(matchedText);
                        break;
                    }
                }
            }
        }
        matchedTexts.removeAll(removeList);
        return matchedTexts;
    }


    private static List<MatchedText> search0(String text, Nfa nfa) {
        List<MatchedText> matchedTexts = new ArrayList<>();

        Set<NfaState> nextStates = new HashSet<>();
        Set<Integer> visitedIndex = new HashSet<>();


        //从起点开始获取下一跳的节点，放入nextStates中
        getNextStates(nextStates, nfa.getStart());

        char[] charArray = text.toCharArray();
        int start = -1;
        int end = 0;

        for (int i = 0; i < charArray.length; ) {
            char c = charArray[i];
            Set<NfaState> currentMatchedStates = new HashSet<>();
            for (NfaState nfaState : nextStates) {
                //把text中的字节，与nfaState进行匹配
                NfaState currentMatchedState = nfaState.getTransition().get(Character.toString(c));
                if (null != currentMatchedState) {
                    //匹配成功，记录开始点
                    if (start == -1) {
                        start = i;
                    }
                    //获取匹配成功节点的下一跳节点。
                    getNextStates(currentMatchedStates, currentMatchedState);
                }
            }
            nextStates = currentMatchedStates;

            if (nextStates.isEmpty()) {
                //如果没有下一跳节点，则重置nfa节点，从头开始匹配
                start = -1;
                getNextStates(nextStates, nfa.getStart());
                if (visitedIndex.contains(i)) {
                    i++;
                } else {
                    visitedIndex.add(i);
                }
            } else {
                //存在下一跳节点，判断下一跳节点中是否存在"结束"节点。
                Optional<NfaState> nfaStateOptional = nextStates.stream().filter(nfaState -> nfaState.isEnd).findAny();
                if (nfaStateOptional.isPresent()) {
                    //如果存在，就成功匹配nfa的一条规则
                    end = i;
                    MatchedText matchedString = new MatchedText(text.substring(start, end + 1), start, end);
                    matchedTexts.add(matchedString);
                    i++;
                } else {
                    i++;
                }
            }
        }
        return matchedTexts;
    }


    /**
     * 获取下一跳的有效节点
     * <p>
     * 1个state存在 2 种情况
     * 1. 只有 epsilon transition情况，跳过当前去下一个state，查看是否存在symbol transition，不存在继续反复操作
     * 2. 只有 symbol transition情况， 把当前state加入nextStates
     *
     * @param nextStates
     * @param first
     */
    private static void getNextStates(Set<NfaState> nextStates, NfaState first) {
        if (!first.isEnd) {
            List<NfaState> nfaStates = first.getEpsilonTransitions();
            if (null != nfaStates && !nfaStates.isEmpty()) {
                //存在 EpsilonTransition 的情况
                for (NfaState tmp : nfaStates) {
                    getNextStates(nextStates, tmp);
                }
            } else {
                //只有symbol transition的情况
                nextStates.add(first);
            }
        } else {
            nextStates.add(first);
        }
    }


}
