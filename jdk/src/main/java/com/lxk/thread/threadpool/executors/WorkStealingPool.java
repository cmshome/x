package com.lxk.thread.threadpool.executors;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * WorkStealingPool
 *
 * @author LiXuekai on 2019/10/9
 */
public class WorkStealingPool {

    /*
        public static ExecutorService newWorkStealingPool() {
            return new ForkJoinPool
                (Runtime.getRuntime().availableProcessors(),
                 ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                 null, true);
        }

        （1）使用available processors来创建newWorkStealingPool，并且将available processors作为其目标并行度
        （2）创建一个线程池，该线程池维护足以支持给定并行度级别的线程，并可以使用多个队列来减少争用。
            并行度级别对应于活动参与或可用于参与任务处理的最大线程数。 实际的线程数可能会动态增长和收缩。
            newWorkStealingPool不能保证提交任务的执行顺序。
     */

    @Test
    public void work() throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();

        /*
         * call方法存在返回值futureTask的get方法可以获取这个返回值。
         * 使用此种方法实现线程的好处是当你创建的任务的结果不是立即就要时，
         * 你可以提交一个线程在后台执行，而你的程序仍可以正常运行下去，
         * 在需要执行结果时使用futureTask去获取即可。
         */
        List<Callable<String>> callableList = IntStream.range(0, 20).boxed().map(i -> (Callable<String>) () -> {
            TimeUnit.SECONDS.sleep(3);
            System.out.println(String.format("当前【%s】线程正在执行>>>", Thread.currentThread().getName()));
            return "callable type thread task：" + i;
        }).collect(Collectors.toList());

        // 执行给定的任务，返回持有他们的状态和结果的所有完成的期待列表。
        executorService.invokeAll(callableList).stream().map(futureTask -> {
            try {
                return futureTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(System.out::println);
    }
}
