package com.lxk.design.pattern.singleton.test;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author LiXuekai on 2022/3/15
 */
public class SunCache {

    private static Map<String, String> CACHE;


    private Map<String, String> getCache() {
        if (CACHE == null || CACHE.isEmpty()) {
            synchronized (SunCache.class) {
                if (CACHE == null || CACHE.isEmpty()) {
                    CACHE = initCache();
                }
            }
        }
        return CACHE;
    }

    private Map<String, String> initCache() {
        Map<String, String> map = Maps.newHashMap();
        System.out.println("初始化cache");
        long a = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            map.put(i + "", i + "");
        }
        System.out.println("执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
        return map;
    }

    public String findInCache(String key) {
        return getCache().get(key);
    }

    public void resetCache() {
        synchronized (SunCache.class) {
            CACHE = initCache();
        }
    }

}
