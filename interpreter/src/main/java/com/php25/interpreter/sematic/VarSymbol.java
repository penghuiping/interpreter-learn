package com.php25.interpreter.sematic;

/**
 * @author penghuiping
 * @date 2020/5/29 14:50
 */
public class VarSymbol extends Symbol {

    public VarSymbol(String name, BuildInTypeSymbol type) {
        this.name = name;
        this.type = type;
    }
}
