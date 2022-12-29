package com.lxk.tool.model;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 自定义的线程安全的list
 *
 * @author LiXuekai on 2022/12/27
 */
public class ConcurrentList<T> {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final List<T> list;


    public ConcurrentList(List<T> list) {
        this.list = list;
    }

    public T get(int index) {
        readLock.lock();
        try {
            return list.get(index);
        } finally {
            readLock.unlock();
        }
    }

    public int size() {
        readLock.lock();
        try {
            return list.size();
        } finally {
            readLock.unlock();
        }
    }

    public boolean isEmpty() {
        readLock.lock();
        try {
            return list == null || list.size() == 0;
        } finally {
            readLock.unlock();
        }
    }

    public boolean contains(Object o) {
        readLock.lock();
        try {
            return list.contains(o);
        } finally {
            readLock.unlock();
        }
    }

    public boolean add(T t) {
        writeLock.lock();
        try {
            return list.add(t);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean remove(Object o) {
        writeLock.lock();
        boolean ret;
        try {
            ret = list.remove(o);
        } finally {
            writeLock.unlock();
        }
        return ret;
    }

    public void clear() {
        writeLock.lock();
        try {
            list.clear();
        } finally {
            writeLock.unlock();
        }
    }

    public T set(int index, T element) {
        writeLock.lock();
        try {
            return list.set(index, element);
        } finally {
            writeLock.unlock();
        }
    }

    public void add(int index, T element) {
        writeLock.lock();
        try {
            list.add(index, element);
        } finally {
            writeLock.unlock();
        }
    }

    public T remove(int index) {
        writeLock.lock();
        try {
            return list.remove(index);
        } finally {
            writeLock.unlock();
        }
    }

}
