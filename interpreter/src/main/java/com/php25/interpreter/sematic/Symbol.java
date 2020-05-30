package com.php25.interpreter.sematic;

import lombok.Getter;

/**
 * @author penghuiping
 * @date 2020/5/29 14:40
 */
@Getter
public class Symbol {
    /**
     * 名称
     */
    protected String name;

    /**
     * 数据类型
     */
    protected BuildInTypeSymbol type;

}
