package com.lxk.tool;

import com.google.common.collect.Lists;
import com.lxk.tool.model.ConcurrentList;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2022/12/29
 */
public class TestConcurrentList {


    @Test
    public void safe() throws InterruptedException {
        Total total = new Total();

        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        service.execute(total);
        service.execute(total);
        service.execute(total);
        service.execute(total);
        service.execute(total);

        service.execute(total);
        service.execute(total);
        service.execute(total);
        service.execute(total);
        service.execute(total);

        TimeUnit.SECONDS.sleep(5);
        // 期望值是10万
        System.out.println(total.list.size());
    }


    /**
     * 模仿处理数据的n个线程，每个线程里面还有个做统计信息的线程。
     */
    static class Total implements Runnable {

        /**
         * 测试线程安全的list
         */
        private final ConcurrentList<Integer> list = new ConcurrentList<>(Lists.newArrayList());


        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
                if (i % 10 == 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + "   over.");
        }
    }
}
