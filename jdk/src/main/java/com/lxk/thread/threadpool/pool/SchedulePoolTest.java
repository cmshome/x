package com.lxk.thread.threadpool.pool;

import com.google.common.collect.Lists;
import com.lxk.tool.monitor.ScheduleUtil;
import com.lxk.tool.monitor.model.MonitorThread;
import com.lxk.tool.util.TimeUtils;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 一旦提交到定时任务的线程池，就没法给干掉了
 *
 * @author LiXuekai on 2022/12/1
 */
public class SchedulePoolTest {


    /**
     * 测试怎么停止已经提交到定时任务线程池的线程，
     * remove不管用，
     * 内部调用Thread.currentThread().interrupt();也不管用
     * 最后，只能在线程中设置逃跑机制，遇到关闭开关，抛异常，就没了，就不再继续定时执行了。
     */
    @Test
    public void stop() throws InterruptedException {
        List<MonitorThread> list = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            MonitorThread monitorThread = new MonitorThread("name" + i);
            monitorThread.getAndIncrement(String.valueOf(i));
            list.add(monitorThread);
            ScheduleUtil.scheduleWith(monitorThread, 5, 5, TimeUnit.SECONDS);
        }
        TimeUnit.SECONDS.sleep(20);
        stop(list);
        TimeUnit.MINUTES.sleep(5);
    }

    /**
     * 还是停不掉，还是在run，空转中。
     */
    private void stop(List<MonitorThread> list) {
        for (MonitorThread monitorThread : list) {
            String name = monitorThread.getName();
            if (name.contains("0")) {
                continue;
            }
            monitorThread.setStop(true);
        }
    }


    private void remove(List<Runnable> list) {
        System.out.println("main sleep 30 over. try to remove one thread from thread pool");
        for (Runnable runnable : list) {
            // remove 了，还能继续运行。。。还是干不掉！
            ScheduleUtil.getScheduledExecutorService().remove(runnable);
            System.out.println("remove one from pool");
        }
    }


    /**
     * schedule任务在整15秒的时间点开始运行
     */
    @Test
    public void delay() throws InterruptedException {
        long l = TimeUtils.nowS();
        System.out.println(l);
        long delay = 15 - l % 15;
        System.out.println(delay);

        ScheduleUtil.scheduleAt(
                () -> {
                    String now = TimeUtils.now();
                    System.out.println(now);
                },
                delay,
                15,
                TimeUnit.SECONDS
        );

        TimeUnit.MINUTES.sleep(5);
    }

    /**
     * 取整：必须的先除法，再乘法才行。
     */
    @Test
    public void abc() {
        int timeDelay = 600;
        long l = TimeUtils.nowS();
        long to = l / timeDelay * timeDelay;
        System.out.println(TimeUtils.formatS(to));
    }


    /**
     * 控制定时任务的运行时间
     */
    @Test
    public void when() throws InterruptedException {
        // 24小时周期
        long period = 24 * 60 * 60;
        LocalDateTime now = LocalDateTime.now().withHour(23).withMinute(58);
        LocalDateTime end = now.plusDays(1).withHour(0).withMinute(0).withSecond(0);
        System.out.println(TimeUtils.format(now));
        System.out.println(TimeUtils.format(end));
        long initialDelay = Math.abs(Duration.between(end, now).getSeconds());
        System.out.println(initialDelay);
        ScheduleUtil.scheduleWith(() -> {
            try {
                System.out.println("run...");
            } catch (Throwable throwable) {
            }
        }, initialDelay, period, TimeUnit.SECONDS);

        TimeUnit.MINUTES.sleep(10);
    }

}
