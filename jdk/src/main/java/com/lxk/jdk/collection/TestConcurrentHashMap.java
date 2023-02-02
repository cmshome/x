package com.lxk.jdk.collection;

import com.google.common.collect.Maps;
import com.lxk.tool.util.CollectionUtil;
import com.lxk.tool.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Objects;

/**
 * @author LiXuekai on 2023/1/5
 */
public class TestConcurrentHashMap {
    private final Map<String, String> map = Maps.newConcurrentMap();


    @Before
    public void init() {
        Map<String, String> stringMap = CollectionUtil.getMap(5);
        map.putAll(stringMap);
    }

    /**
     * 因为线程安全的，所以，update是安全的
     */
    @Test
    public void update() {
        System.out.println(JsonUtils.parseObjToJson(map));
        for (String key : map.keySet()) {
            if (Objects.equals(key, "3")) {
                map.put(key, "abc");
            }
        }
        System.out.println(JsonUtils.parseObjToJson(map));
    }

    /**
     * 因为线程安全的，所以，remove是安全的
     */
    @Test
    public void remove() {
        System.out.println(JsonUtils.parseObjToJson(map));
        for (String key : map.keySet()) {
            if (Objects.equals(key, "3")) {
                map.remove(key);
            }
        }
        System.out.println(JsonUtils.parseObjToJson(map));
    }

    /**
     * 竟然不知道 ConcurrentMap 是不允许key value 为null的。
     */
    @Test
    public void putNull() {
        String a = map.put("a", null);
        System.out.println(a);
    }


}
