package com.php25.interpreter.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penghuiping
 * @date 2019/10/17 13:10
 */
public class GlobalMemory {

    private final static GlobalMemory _instance = new GlobalMemory();

    private static final Map<String, Object> _table = new HashMap<>();

    private GlobalMemory() {

    }

    public static GlobalMemory getInstance() {
        return GlobalMemory._instance;
    }

    public static void assignVar(String varName, Object varValue) {
        _table.put(varName, varValue);
    }

    public static Object getVarValue(String varName) {
        return _table.get(varName);
    }

    public static String getPrintContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : _table.keySet()) {
            stringBuilder.append(key).append(":").append(_table.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }


}
