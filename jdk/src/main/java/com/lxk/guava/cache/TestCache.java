package com.lxk.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2023/11/29
 */
public class TestCache {

    private LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return loadByKye(key);
                }
            });

    private String loadByKye(String key) {
        System.out.println("load by key");
        return key;
    }

    @Test
    public void cache() throws Exception {
        while (true){
            System.out.println(cache.get("a"));
            TimeUnit.SECONDS.sleep(1);
        }

    }
}
