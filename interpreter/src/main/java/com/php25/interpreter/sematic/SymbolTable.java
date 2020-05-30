package com.php25.interpreter.sematic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penghuiping
 * @date 2020/5/29 15:04
 */
public class SymbolTable {
    private Map<String, Symbol> map = new HashMap<>();

    public SymbolTable() {
        initBuildInType();
    }

    private void initBuildInType() {
        BuildInTypeSymbol objectSymbol = new BuildInTypeSymbol("object");
        BuildInTypeSymbol intSymbol = new BuildInTypeSymbol("int");
        BuildInTypeSymbol floatSymbol = new BuildInTypeSymbol("float");
        this.map.put(objectSymbol.getName(), objectSymbol);
        this.map.put(intSymbol.getName(), intSymbol);
        this.map.put(floatSymbol.getName(), floatSymbol);
    }

    public void insert(Symbol symbol) {
        map.put(symbol.name, symbol);
    }

    public Symbol lookup(String name) {
        return map.get(name);
    }
}
