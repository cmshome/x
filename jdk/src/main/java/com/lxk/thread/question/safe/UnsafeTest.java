package com.lxk.thread.question.safe;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2022/9/29
 */
public class UnsafeTest {

    @Test
    public void safe() throws InterruptedException {
        Total total = new Total();
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        service.execute(total);
        service.execute(total);
        TimeUnit.SECONDS.sleep(3);
        total.out();
    }

    static class Total implements Runnable {

        private final ConcurrentHashMap<String, Integer> statMap = new ConcurrentHashMap<>();

        private static final String COUNT = "count";


        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                Integer count = statMap.get(COUNT);
                if (count == null) {
                    count = 0;
                }
                // 不安全的地方，比如都拿到count=100，之后都加1，然后都算个101，put进去了。
                statMap.put(COUNT, count + 1);
            }
            System.out.println("run over");
        }

        public void out() {
            statMap.forEach((k, v) -> {
                System.out.println(k + "   " + v);
            });
        }
    }



}
