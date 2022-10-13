package com.lxk.tool.monitor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 功能单一，才能是工具类。我这就提供一个线程池。
 *
 * @author LiXuekai on 2020/12/15
 */
public final class MonitorService {
    /**
     * 提交的任务超过max core size，他首先会把多余的给放到缓存队列中，队列放不下了，才会启动大于core size 个的线程去执行任务，当超过maximumPoolSize 的时候，执行拒绝策略。
     */
    public static final int CORE_POOL_SIZE = 10;
    private static final int DELAY = 60;

    private static volatile ScheduledExecutorService monitorExecutor;


    /**
     * 获得单例
     *
     * @return monitorExecutor
     */
    public static ScheduledExecutorService getMonitorExecutor() {
        if (monitorExecutor == null) {
            synchronized (MonitorService.class) {
                if (monitorExecutor == null) {
                    System.out.println("MonitorService -> init monitor thread start...");
                    ThreadFactory monitorThreadFactory = new ThreadFactoryBuilder().setNameFormat("monitorThreadPool-%d").build();
                    monitorExecutor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, monitorThreadFactory, new ThreadPoolExecutor.AbortPolicy());
                    System.out.println("MonitorService -> init monitor thread success ...");
                }
            }
        }
        return monitorExecutor;
    }


    /**
     * scheduleAtFixedRate
     * 指的是“以固定的频率”执行，period（周期）指的是两次成功执行之间的时间。
     * 上一个任务开始的时间计时，一个period后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，
     * 如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
     * FixedRate: 固定比率
     *
     * @param monitorThread 线程
     */
    public static void scheduleAt(Runnable monitorThread) {
        getMonitorExecutor().scheduleAtFixedRate(monitorThread, DELAY, DELAY, TimeUnit.SECONDS);
    }

    /**
     * scheduleWithFixedDelay
     * 指的是“以固定的延时”执行，delay（延时）指的是一次执行终止和下一次执行开始之间的延迟。
     *
     * @param monitorThread 线程
     */
    public static void scheduleWith(Runnable monitorThread) {
        getMonitorExecutor().scheduleWithFixedDelay(monitorThread, DELAY, DELAY, TimeUnit.SECONDS);
    }

}
