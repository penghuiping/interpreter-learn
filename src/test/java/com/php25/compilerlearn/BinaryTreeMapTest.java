package com.php25.compilerlearn;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author penghuiping
 * @date 2019/10/12 18:26
 */
@Slf4j
public class BinaryTreeMapTest {

    @Test
    public void test() {
        BinaryTreeMap<String,String> binaryTreeMap = new BinaryTreeMap<>();
        binaryTreeMap.put("key1","1");
        binaryTreeMap.put("key2","2");
        binaryTreeMap.put("key3","3");
        binaryTreeMap.put("key4","4");
        binaryTreeMap.put("key5","5");
        binaryTreeMap.put("key6","6");
        binaryTreeMap.put("key7","7");
        binaryTreeMap.put("key8","8");
        binaryTreeMap.put("key9","9");
        binaryTreeMap.put("key10","10");
        log.info(binaryTreeMap.toString());
    }
}
