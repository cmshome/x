package com.lxk.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2025/3/4
 */
public class TestCache2 {

    private final LoadingCache<String, Map<String, Map<String, Object>>> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS)
            .build(new CacheLoader<String, Map<String, Map<String, Object>>>() {
                @Override
                public Map<String, Map<String, Object>> load(String key) {
                    return loadByKye(key);
                }
            });

    private Map<String, Map<String, Object>> loadByKye(String key) {
        System.out.println("load by key");
        ImmutableMap<String, Object> allLabel = new ImmutableMap.Builder<String, Object>().put("label", "all").put("value", 100).build();
        Map<String, Object> tmp = new HashMap<>();
        tmp.put("all", allLabel);

        Map<String, Map<String, Object>> result = new HashMap<>();
        result.put("aaa", tmp);
        return result;
    }

    @Test
    public void cache() throws Exception {
        for (int i = 0; i < 1000; i++) {
            Map<String, Map<String, Object>> map = cache.get("" + i);
        }
        TimeUnit.HOURS.sleep(1);
    }
}
