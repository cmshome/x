package com.lxk.guava.collection;

import com.google.common.collect.EvictingQueue;
import org.junit.Test;

/**
 * 这个类不是线程安全的，不接受null元素
 *
 * @author LiXuekai on 2025/5/20
 */
public class EvictingQueueTest {

    /**
     * 固定大小的队列，满了则自动出队。
     */
    @Test
    public void EvictingQueue() {
        // 容量为3
        EvictingQueue<String> queue = EvictingQueue.create(3);
        queue.add("A");
        queue.add("B");
        queue.add("C");
        show(queue);

        queue.add("D");
        show(queue);

    }

    private void show(EvictingQueue<String> queue) {
        queue.forEach(System.out::println);
    }
}
