package com.lxk.jdk.collection;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2024/10/25
 */
public class TestVector {
    private ExecutorService pool;

    @Before
    public void init() {
        pool = Executors.newFixedThreadPool(12);
    }


    @Test
    public void safe() throws InterruptedException {
        System.out.println("run");

        List<String> list = new Vector<>();
        for (int i = 0; i < 10; i++) {
            list.add("a" + i);
        }
        System.out.println(list.size());

        Writer writer = new Writer(list);
        Reader reader = new Reader(list);
        pool.submit(writer);
        pool.submit(reader);
        TimeUnit.MINUTES.sleep(10);
    }


    public static class Writer implements Runnable {
        private final List<String> list;

        public Writer(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            System.out.println("Writer run.");
            for (int i = 0; i < 20000; i++) {
                list.add("b" + i);
            }
        }
    }


    public static class Reader implements Runnable {
        private final List<String> list;

        public Reader(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            System.out.println("Reader run.");
            // 直接用Vector循环，因为线程安全，这儿得一直等，直到写线程释放了，这儿才能读。
            ArrayList<String> temp = Lists.newArrayList(list);
            for (String s : temp) {
                System.out.println("reader temp " + s);
            }
        }
    }
}
