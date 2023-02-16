package com.lxk.guava.collection;

import org.junit.Test;

/**
 * guava ImmutableMap 测试实例
 * <p>
 * @author lxk on 2016/11/14
 */
public class ImmutableMapTest {

    /**
     * 测试 guava ImmutableMap
     */
    @Test
    public void immutableMapTest() {
        Integer keyValue = 30;
        System.out.println("keyValue = " + keyValue + "的提示语是：" + ConstantMap.INTEGER_STRING_MAP.get(keyValue));

        ConstantMap.MAP.forEach((key,value) -> System.out.println("key：" + key + " value：" + value));
    }
}