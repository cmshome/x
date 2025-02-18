package com.lxk.thread.lock.stampedlock;

import org.junit.Test;

import java.util.concurrent.locks.StampedLock;

/**
 * @author LiXuekai on 2025/2/18
 */
public class TestStampedLock {
    private final StampedLock stampedLock = new StampedLock();
    private int count = 0;


    public void writeLockExample() {
        long stamp = stampedLock.writeLock();
        try {
            // 写操作
            count++;
            System.out.println("Write lock acquired, count incremented to: " + count);
        } finally {
            stampedLock.unlockWrite(stamp);
            System.out.println("Write lock released");
        }
    }

    public int readLockExample() {
        long stamp = stampedLock.readLock();
        try {
            // 读操作
            int tempCount = count;
            System.out.println("Read lock acquired, count is: " + tempCount);
            return tempCount;
        } finally {
            stampedLock.unlockRead(stamp);
            System.out.println("Read lock released");
        }
    }

    public int optimisticReadLockExample() {
        long stamp = stampedLock.tryOptimisticRead();
        int tempCount = count;
        // 验证戳记是否仍然有效
        if (!stampedLock.validate(stamp)) {
            // 如果戳记无效，则获取读锁并重新读取
            stamp = stampedLock.readLock();
            try {
                tempCount = count;
                System.out.println("Optimistic read failed, read lock acquired, count is: " + tempCount);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        } else {
            System.out.println("Optimistic read lock acquired, count is: " + tempCount);
        }
        return tempCount;
    }


    @Test
    public void stampedLock() {
        TestStampedLock lock = new TestStampedLock();
        new Thread(lock::writeLockExample).start();
        new Thread(lock::readLockExample).start();
        new Thread(lock::optimisticReadLockExample).start();
    }
}
