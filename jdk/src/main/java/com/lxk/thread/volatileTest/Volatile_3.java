package com.lxk.thread.volatileTest;

import org.junit.Test;

/**
 * Java 并发编程之 测试 重排序
 *
 * @author LiXuekai on 2021/3/31
 */
public class Volatile_3 {
    int a = 1;
    boolean status = false;

    /**
     * 理论上，1、2两行代码可能会重排序，导致3、4执行的是a还没被赋值，结果是有可能是2的。
     * 但是好难重现哦。
     */
    @Test
    public void reorder() {
        while (true) {
            new Thread(this::changeStatus).start();
            new Thread(this::run).start();
        }
    }

    /**
     * 状态切换为true
     */
    public void changeStatus() {
        a = 2;   //1
        status = true;  //2
    }

    /**
     * 若状态为true，则为running
     */
    public void run() {
        if (status) {   //3
            int b = a + 1;  //4
            if (b == 2) {
                System.out.println(b);
            }
        }
    }
}

