package com.php25.interpreter.sematic;

/**
 * @author penghuiping
 * @date 2020/5/29 14:40
 */
public class Symbol {
    /**
     * 名称
     */
    protected String name;

    /**
     * 数据类型
     */
    protected BuildInTypeSymbol type;


    public String getName() {
        return name;
    }

    public BuildInTypeSymbol getType() {
        return type;
    }
}
