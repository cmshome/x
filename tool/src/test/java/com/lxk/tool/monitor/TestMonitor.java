package com.lxk.tool.monitor;

import com.lxk.tool.util.TimeUtils;
import com.lxk.tool.monitor.model.MonitorThread;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 关于定时任务的一些测试
 *
 * @author LiXuekai on 2021/6/15
 */
public class TestMonitor {

    @Before
    public void init() {

    }

    /**
     * 提交的任务超过max core size，他首先会把多余的给放到缓存队列中，队列放不下了，才会启动大于core size 个的线程去执行任务，
     * 当超过maximumPoolSize 的时候，执行拒绝策略。
     */
    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            MonitorThread monitorThread = new MonitorThread("name" + i);
            monitorThread.getAndIncrement(String.valueOf(i));
            MonitorService.getMonitorExecutor().scheduleWithFixedDelay(monitorThread, 5, 5, TimeUnit.SECONDS);
        }

        TimeUnit.MINUTES.sleep(5);

    }

    /**
     * scheduleWithFixedDelay
     * 指的是“以固定的延时”执行，delay（延时）指的是一次执行终止和下一次执行开始之间的延迟。
     * 本test现象：执行完歇息1秒，再下次执行
     */
    @Test
    public void with() throws InterruptedException {
        MonitorService.getMonitorExecutor().scheduleWithFixedDelay(() -> {
            try {
                System.out.println("线程 run 了。。。。。。" + TimeUtils.nowS());
                TimeUnit.SECONDS.sleep(5);
                System.out.println("线程 sleep 完了。。。。。。" + TimeUtils.nowS());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);

        TimeUnit.MINUTES.sleep(500);

    }

    /**
     * scheduleAtFixedRate
     * 指的是“以固定的频率”执行，period（周期）指的是两次成功执行之间的时间。
     * 上一个任务开始的时间计时，一个period后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，
     * 如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
     * FixedRate: 固定比率
     * 本test现象：执行完，没有歇息，直接开始下一次。
     */
    @Test
    public void at() throws InterruptedException {
        MonitorService.getMonitorExecutor().scheduleAtFixedRate(() -> {
            try {
                System.out.println("线程 run 了。。。。。。" + TimeUtils.nowS());
                TimeUnit.SECONDS.sleep(5);
                System.out.println("线程 sleep 完了。。。。。。" + TimeUtils.nowS());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);

        TimeUnit.MINUTES.sleep(500);
    }


}
