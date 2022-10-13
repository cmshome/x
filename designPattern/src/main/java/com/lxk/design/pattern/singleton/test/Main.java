package com.lxk.design.pattern.singleton.test;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2022/3/15
 */
public class Main {
    private final ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private final SunCache sunCache = new SunCache();


    @Test
    public void test() throws InterruptedException {
        service.execute(() -> {
            while (true) {
                sunCache.resetCache();
            }
        });

        service.execute(() -> {
            while (true) {
                find();
            }
        });

        TimeUnit.MINUTES.sleep(5);

    }

    private void find() {
        String s = sunCache.findInCache("4");
        System.out.println("根据key从缓存查询的值是：" + s);
    }


    @Test
    public void one() {
        find();
    }

    @Test
    public void threadFind() throws InterruptedException {
        service.execute(() -> {
            while (true) {
                find();
            }
        });
        TimeUnit.MINUTES.sleep(5);
    }
}
