package com.lxk.thread.threadpool.executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lxk.tool.util.ThreadUtils;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 创建容量为1的缓冲池
 * 提交的多个任务，在没有可用线程的时候，就被堵在一个无界队列中，等待执行。
 *
 * @author lxk on 2018/10/8
 */
public class SingleThreadPool {
    private static final int MAX = 40;

    @Test
    public void single() throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < MAX; i++) {
            int index = i + 1;
            service.execute(() -> {
                ThreadUtils.sleep(100);
                System.out.println("HeartBeat........................ index is " + index);
            });
        }

        System.out.println("main thread run ");
        TimeUnit.SECONDS.sleep(1000);
        service.shutdown();
    }

    /**
     * 推荐初始化线程池的方式
     */
    private static void positive() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("SingleThreadPool-%d").build();
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        //这个executor被一个内部类 FinalizableDelegatedExecutorService 使用了，外部无法创建。。。
    }
}
