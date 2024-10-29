package com.lxk.jdk.queue;

import com.google.common.collect.Queues;
import com.lxk.tool.util.FixedThreadPool;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 应用场景
 * 生产者-消费者模式
 * 任务调度
 *
 * @author LiXuekai on 2024/10/25
 */
public class TestLinkedBlockingQueue {


    @Test
    public void safe() throws InterruptedException {
        System.out.println("run");
        LinkedBlockingQueue<String> queue = Queues.newLinkedBlockingQueue(10000);
        Writer writer = new Writer(queue);
        Reader reader = new Reader(queue);
        FixedThreadPool.submitTask(writer);
        FixedThreadPool.submitTask(reader);
        TimeUnit.MINUTES.sleep(10);
    }


    /**
     * 入队方法
     * put(E e)
     * 队列已满，阻塞等待
     * 队列未满，创建一个node节点放入队列中，如果放完以后队列还有剩余空间，继续唤醒下一个添加线程进行添加。如果放之前队列中没有元素，放完以后要唤醒消费线程进行消费
     * offer(E e)
     * offer(E e, long timeout, TimeUnit unit)
     * offer仅仅对put方法改动了一点点，当队列没有可用元素的时候，不同于put方法的阻塞等待，offer方法直接方法false。
     */
    public static class Writer implements Runnable {
        private final LinkedBlockingQueue<String> queue;

        public Writer(LinkedBlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("Writer run.");
            for (int i = 0; i < 20000; i++) {
                String s = "b" + i;
                System.out.println("queue offer " + s);
                // 不同于put方法的阻塞等待，offer方法直接方法false。
                queue.offer(s);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Writer run over.");
        }
    }

    /**
     * 出队方法
     * E take();
     * 队列为空，阻塞等待。
     * 队列不为空，从队首获取并移除一个元素，如果消费后还有元素在队列中，继续唤醒下一个消费线程进行元素移除。如果放之前队列是满元素的情况，移除完后要唤醒生产线程进行添加元素
     * E poll();
     * E poll(long timeout, TimeUnit unit);
     * poll方法去除了take方法中元素为空后阻塞等待这一步骤
     */
    public static class Reader implements Runnable {
        private final LinkedBlockingQueue<String> queue;

        public Reader(LinkedBlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("Reader run.");
            while (queue.size() > 0) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    // take方法队列为空，阻塞等待。
                    String take = queue.take();
                    System.out.println("Reader:" + take + "   queue size =" + queue.size());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Reader run over.");
        }
    }
}
