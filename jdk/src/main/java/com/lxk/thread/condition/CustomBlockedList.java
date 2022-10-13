package com.lxk.thread.condition;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义阻塞list
 * 需要实现2个条件：
 * 队列不满     因为队列如果已满，则不可再做入队操作
 * 队列不空     因为空队列没有元素，所以不可以做出队操作
 *
 * @author LiXuekai on 2021/5/12
 */
public class CustomBlockedList<T> {
    private final Lock lock = new ReentrantLock();
    /**
     * list不满
     */
    private final Condition notFull = lock.newCondition();
    /**
     * list不空
     */
    private final Condition notEmpty = lock.newCondition();
    /**
     * 共享变量，互斥访问。
     */
    private final List<T> list = Lists.newArrayList();
    private static final int MAX = 10;


    public void in(T in) {
        lock.lock();
        try {
            if (list.size() == MAX) {
                try {
                    System.out.println("list 已经满了，不能再往里面进了。");
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(in);
            String name = Thread.currentThread().getName();
            info(name + "-in");
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T out() {
        T out;
        lock.lock();
        try {
            if (list.isEmpty()) {
                try {
                    System.out.println("list 是空的，无元素可出。");
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            out = list.remove(0);
            String name = Thread.currentThread().getName();
            info(name + "-out");
            notFull.signal();
        } finally {
            lock.unlock();
        }

        return out;
    }

    public void info(String info){
        //System.out.println(info + " list 中的元素是：" + list.toString());
    }
}
