package com.php25.interpreter.sematic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penghuiping
 * @date 2020/5/29 15:04
 */
public class SymbolTable {
    private SymbolTable parent;

    private final Map<String, Symbol> map = new HashMap<>();

    public SymbolTable(SymbolTable parent) {
        if (null == parent) {
            initBuildInType();
        } else {
            this.parent = parent;
        }
    }

    private void initBuildInType() {
        BuildInTypeSymbol objectSymbol = new BuildInTypeSymbol("object");
        BuildInTypeSymbol intSymbol = new BuildInTypeSymbol("int");
        BuildInTypeSymbol floatSymbol = new BuildInTypeSymbol("float");
        BuildInTypeSymbol functionSymbol = new BuildInTypeSymbol("function");
        this.map.put(objectSymbol.getName(), objectSymbol);
        this.map.put(intSymbol.getName(), intSymbol);
        this.map.put(floatSymbol.getName(), floatSymbol);
        this.map.put(functionSymbol.getName(), functionSymbol);
    }

    public void insert(Symbol symbol) {
        map.put(symbol.name, symbol);
    }

    public Symbol lookup(String name) {
        //当前SymbolTable没有的话，去父级节点获取，一直到root节点
        SymbolTable current = this;
        Symbol result = null;
        while (true) {
            result = current.map.get(name);
            if (result != null) {
                break;
            }

            //去父级节点
            if (null == current.parent) {
                //没有父节点，说明此节点是root节点,退出循环
                break;
            }
            current = current.parent;
        }
        return result;
    }

    public boolean exists(String name) {
        return this.lookup(name) != null;
    }


    public SymbolTable getParent() {
        return parent;
    }
}
