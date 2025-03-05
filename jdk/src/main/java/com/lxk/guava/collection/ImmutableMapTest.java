package com.lxk.guava.collection;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * guava ImmutableMap 测试实例
 * <p>
 *
 * @author lxk on 2016/11/14
 */
public class ImmutableMapTest {
    Map<Integer, String> INTEGER_STRING_MAP =
            new ImmutableMap.Builder<Integer, String>()

                    .put(30, "a")
                    .put(31, "b")
                    .put(32, "c")
                    .put(33, "d")
                    .put(34, "e")
                    .put(38, "f")
                    .put(39, "g(d)")
                    .put(40, "h")
                    .put(41, "i")
                    .put(42, "j")
                    .put(43, "k")

                    .build();

    /**
     * 这个最多支持5对，不能再多啦。
     * 后期又扩展了，竟然支持10对了。
     */
    ImmutableMap<String, String> MAP = ImmutableMap.of("key1", "value1", "key2", "value2");

    ImmutableMap<String, String> MAP_ = ImmutableMap.copyOf(Maps.newHashMap());

    /**
     * 测试 guava ImmutableMap
     */
    @Test
    public void immutableMapTest() {
        Integer keyValue = 30;
        System.out.println("keyValue = " + keyValue + "的提示语是：" + INTEGER_STRING_MAP.get(keyValue));

        MAP.forEach((key, value) -> System.out.println("key：" + key + " value：" + value));
    }

    @Test
    public void initMap() {
        ImmutableMap<String, String> of = ImmutableMap.of("1", "1", "2", "2");

        // 不可变集合，不让写操作，只能读
        //of.put("3","3");
        //of.remove("3","3");

        HashMap<String, String> map = Maps.newHashMap();
        map.putAll(of);
        map.remove("1");
        map.remove("2");
        System.out.println(map.size());
    }

}