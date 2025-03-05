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

            // 当缓存条目数接近 maximumSize 时，Guava Cache 会主动淘汰低使用频率的条目，而非严格等待达到上限‌
            .maximumSize(1000)

            /*
             * 权重计算更精准控制内存消耗‌
             * 同一缓存实例中 maximumWeight 与 maximumSize 不可共存，否则启动时报错‌
             */
            //.maximumWeight(1000)
            //.weigher((k, v) -> k.toString().length())

            .expireAfterAccess(2, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return loadByKye(key);
                }
            });

    private String loadByKye(String key) {
        System.out.println("load by key " + key);
        return key;
    }

    @Test
    public void cache() throws Exception {
        long a = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            cache.get("" + i);
            // 实际上 size不会到1000
            System.out.println(cache.size());
        }
        System.out.println("执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");

    }
}
