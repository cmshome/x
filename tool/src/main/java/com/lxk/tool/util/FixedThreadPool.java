package com.lxk.tool.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * FixedThreadPool
 *
 * @author LiXuekai on 2023/8/22
 */
public class FixedThreadPool {

    /**
     * 线程池
     */
    private static volatile ThreadPoolExecutor executors;
    private static final int CORE_SIZE = 10;


    /**
     * 根据配置初始化线程池大小
     */
    public static void initExecutors(int poolSize) {
        if (executors == null) {
            synchronized (FixedThreadPool.class) {
                if (executors == null) {
                    executors = newFixedThreadPool(poolSize);
                }
            }
        }
    }

    /**
     * 创建一个fixed的线程池，最多只允许运行
     *
     * @return 线程池
     */
    private static ThreadPoolExecutor newFixedThreadPool(int poolSize) {
        String nameFormat = "fixed-pool" + "-%d";
        return new ThreadPoolExecutor(
                poolSize,
                poolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat(nameFormat).build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 提交线程任务
     */
    public static void submitTask(Runnable task) {
        if (executors == null) {
            initExecutors(CORE_SIZE);
        }
        executors.execute(task);
    }

    /**
     * 返回在队列中等待运行的任务数
     */
    public static int getQueueSize() {
        return executors.getQueue().size();
    }
}
