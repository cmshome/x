package com.lxk.jdk.number;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author LiXuekai on 2023/4/7
 */
public class TestNumber {
    private final Map<String, Object> map = Maps.newHashMap();


    @Before
    public void init() {
    }

    @Test
    public void test() {
        String k1 = "ddd";
        String k2 = "iii";
        String k3 = "lll";
        map.put(k1,123.12);
        map.put(k2,123);
        map.put(k3,12313131);

        while (true) {
            getDouble(map, k1, 0.);
            getInteger(map, k2, 0);
            getLong(map, k3, 0L);
        }
    }

    public Double getDouble(Map<String, Object> map, String key, Double def) {
        try {
            Object o = map.get(key);
            return Double.parseDouble(o.toString());
        } catch (Exception ignored) {
            return def;
        }
    }

    public Integer getInteger(Map<String, Object> map, String key, Integer def) {
        try {
            Object o = map.get(key);
            return Integer.parseInt(o.toString());
        } catch (Exception ignored) {
            return def;
        }
    }

    public Long getLong(Map<String, Object> map, String key, Long def) {
        try {
            Object o = map.get(key);
            return Long.parseLong(o.toString());
        } catch (Exception ignored) {
            return def;
        }
    }

}
