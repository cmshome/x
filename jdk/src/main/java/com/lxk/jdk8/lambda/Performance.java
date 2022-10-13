package com.lxk.jdk8.lambda;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 测试性能(Java 8 的循环和Java 7 的循环耗时的对比测试)
 * <p>
 *
 * @author lxk on 2017/8/31
 */
public class Performance {

    private final List<String> list = Lists.newArrayList();

    @Before
    public void init() {
        for (int i = 0; i < 10000; i++) {
            list.add("item " + i);
        }
    }

    @Test
    public void notNormalAfterLoop() {
        long a = System.currentTimeMillis();
        list.stream().parallel().forEach(System.out::print);
        System.out.println(" list.stream().parallel().forEach 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    @Test
    public void normalAfterLoop() {
        long a = System.currentTimeMillis();
        list.forEach(System.out::print);
        System.out.println(" list.stream().forEach 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
        a = System.currentTimeMillis();
        list.forEach(System.out::print);
        System.out.println(" list.forEach 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    @Test
    public void normalBeforeLoop() {
        long a = System.currentTimeMillis();
        for (String s : list) {
            System.out.print(s);
        }
        System.out.println(" for each 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

}
