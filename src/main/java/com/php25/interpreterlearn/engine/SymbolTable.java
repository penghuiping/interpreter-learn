package com.php25.interpreterlearn.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penghuiping
 * @date 2019/10/17 13:10
 */
public class SymbolTable {


    public static final Map<String, Object> _table = new HashMap<>();

    public  final Map<String, Object> local_table = new HashMap<>();


}
