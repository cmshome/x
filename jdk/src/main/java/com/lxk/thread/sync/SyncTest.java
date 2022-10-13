package com.lxk.thread.sync;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author LiXuekai on 2021/5/18
 */
public class SyncTest {

    /**
     * 所有同步在一个对象上的同步块在同时只能被一个线程进入并执行操作。
     * 所有其他等待进入该同步块的线程将被阻塞，直到执行该同步块中的线程退出。
     */
    @Test
    public void test() throws InterruptedException {

        new Thread(Two::one).start();

        new Thread(Two::two).start();

        // 不sleep，test线程没了，两个子线程也跟着gg了。
        TimeUnit.MINUTES.sleep(3);

    }

}
