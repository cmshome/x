package com.lxk.tool.monitor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 定时任务的线程池，提交耗时短的定时任务
 *
 * @author LiXuekai on 2021/12/28
 */
public final class ScheduleUtil {
    /**
     * 已经不小了
     */
    private static final int CORE_POOL_SIZE = 20;
    private static final int DELAY = 15;
    private static volatile ScheduledThreadPoolExecutor scheduledExecutorService;


    public static ScheduledThreadPoolExecutor getScheduledExecutorService() {
        if (scheduledExecutorService == null) {
            synchronized (ScheduleUtil.class) {
                if (scheduledExecutorService == null) {
                    ThreadFactory monitorThreadFactory = new ThreadFactoryBuilder().setNameFormat("scheduleThreadPool-%d").build();
                    scheduledExecutorService = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, monitorThreadFactory, new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return scheduledExecutorService;
    }

    /**
     * scheduleAtFixedRate
     * 指的是“以固定的频率”执行，period（周期）指的是两次成功执行之间的时间。
     * 上一个任务开始的时间计时，一个period后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，
     * 如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
     * FixedRate: 固定比率
     * （提交的任务，立马执行一次。）
     *
     * @param monitorThread 线程
     */
    public static void scheduleAt(Runnable monitorThread) {
        scheduleAt(monitorThread, 0, DELAY, TimeUnit.SECONDS);
    }

    /**
     *
     * scheduleAtFixedRate
     * 指的是“以固定的频率”执行，period（周期）指的是两次成功执行之间的时间。
     * 上一个任务开始的时间计时，一个period后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，
     * 如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
     * FixedRate: 固定比率
     *（提交的任务，延迟一下子再执行。）
     *
     * @param monitorThread 线程
     */
    public static void scheduleAt_(Runnable monitorThread) {
        scheduleAt(monitorThread, DELAY, DELAY, TimeUnit.SECONDS);
    }

    public static void scheduleAt(int seconds, Runnable monitorThread) {
        scheduleAt(monitorThread, seconds, seconds, TimeUnit.SECONDS);
    }

    /**
     * scheduleAt 多参数重载
     *
     * @param monitorThread 任务
     * @param initialDelay  延迟
     * @param period        间隔
     * @param unit          时间单位
     */
    public static void scheduleAt(Runnable monitorThread, long initialDelay, long period, TimeUnit unit) {
        getScheduledExecutorService().scheduleAtFixedRate(monitorThread, initialDelay, period, unit);
    }

    /**
     * scheduleWithFixedDelay
     * 指的是“以固定的延时”执行，delay（延时）指的是一次执行终止和下一次执行开始之间的延迟。
     *
     * @param monitorThread 线程
     */
    public static void scheduleWith(Runnable monitorThread) {
        scheduleWith(monitorThread, DELAY, DELAY, TimeUnit.SECONDS);
    }

    /**
     * scheduleWith 多参数重载
     *
     * @param monitorThread 任务
     * @param initialDelay  延迟
     * @param period        间隔
     * @param unit          时间单位
     */
    public static void scheduleWith(Runnable monitorThread, long initialDelay, long period, TimeUnit unit) {
        getScheduledExecutorService().scheduleWithFixedDelay(monitorThread, initialDelay, period, unit);
    }
}
