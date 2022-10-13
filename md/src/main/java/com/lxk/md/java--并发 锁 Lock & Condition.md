# java--并发 锁 Lock & Condition



## 并发编程的关键是什么 ？
* 互斥  
同一时刻，只允许一个线程访问共享资源
* 同步  
线程之间通信、协作


## JUC是通过Lock、Condition接口实现的管程
* Lock  
解决互斥
* Condition  
解决同步


## 既生 synchronized 何生 Lock ？
在JDK 1.5，synchronized性能差于Lock，但1.6后，synchronized被优化，将性能提高，所以1.6后又推荐使用synchronized。    
所以并不是因为性能问题才生的lock。  
问题的关键在于：死锁问题的`破坏"不可抢占"`条件。使用synchronized无法达到该目的。  
因为synchronized申请资源时，若申请不到，线程直接就被阻塞了，而阻塞态的线程是无所作为，自然也释放不了线程已经占有的资源。  
但我们希望：对于“不可抢占”条件，占用部分资源的线程进一步申请其他资源时，若申请不到，可以主动释放它已占有的资源，这样“不可抢占”条件就被破坏掉了。  
  
若重新设计一把互斥锁去解决这个问题，咋搞呢？如下设计才能破坏“不可抢占”条件：  
* 能响应中断  
使用synchronized持有 锁X 后，若尝试获取 锁Y 失败，则线程进入阻塞，一旦死锁，就再无机会唤醒阻塞线程。但若阻塞态的线程能够响应中断信号，即当给阻塞线程发送中断信号时，能唤醒它，那它就有机会释放曾经持有的 锁X。  
* 支持超时  
若线程在一段时间内，都没有获取到锁，不是进入阻塞态，而是返回一个错误，则该线程也有机会释放曾经持有的锁
* 非阻塞地获取锁  
如果尝试获取锁失败，并不进入阻塞状态，而是直接返回，那这个线程也有机会释放曾经持有的锁
  
 
其实就是Lock接口的如下方法：
* lockInterruptibly() 支持中断
* tryLock(long time, TimeUnit unit) 支持超时 
* tryLock() 支持非阻塞获取锁 

## 是如何保证可见性的
Java多线程的可见性是通过Happens-Before规则保证的，而Happens-Before 并没有提到 Lock 锁。  
Lock经典案例就是try/finally，必须在finally块里释放锁。  
它是利用了volatile的Happens-Before规则。因为 ReentrantLock 的内部类继承了 AQS，其内部维护了一个volatile 变量state  
lock() 获取锁时，会读写state  
unlock() 解锁时，也会读写state   

## 可重入锁？
可重入锁，就是线程可以重复获取同一把锁  
```java
public class LockTest2 {
    private final Lock reentrantLock = new ReentrantLock(true);
    int value;

    public int get() {
        // 2，获取锁
        // 此时，若锁可重入，则t1 可再次加锁
        //      若不可重入，则t1 此时会被阻塞
        reentrantLock.lock();
        try {
            return value;
        } finally {
            // 保证锁的释放
            reentrantLock.unlock();
        }
    }

    public void add() {
        // t1 获取锁
        reentrantLock.lock();
        try {
            // 1，线程t1 已经获取锁了，
            value = get() + 1;
        } finally {
            // 保证锁的释放
            reentrantLock.unlock();
        }
    }

    @Test
    public void testReentrant() {
        LockTest2 lockTest2 = new LockTest2();
        lockTest2.add();
        int i = lockTest2.get();
        System.out.println(i);
    }
}
``` 

## 公平锁与非公平锁
比如ReentrantLock有两个构造器，一个是无参构造器，一个是传入fair参数的。fair参数代表锁的公平策略，true：需要构造一个公平锁，false：构造一个非公平锁（默认）。   
内部类 FairSync NonfairSync

## 锁的入口等待队列
每个锁都对应一个等待队列，如果一个线程没有获得锁，就会进入等待队列，当有线程释放锁的时候，就需要从等待队列中唤醒一个等待的线程。若是公平锁，唤醒策略就是谁等待的时间长，就唤醒谁，这很公平。若是非公平锁，则不提供这个公平保证，所以可能等待时间短的线程被先唤醒。非公平锁的场景还可以是线程释放锁之后，如果来了一个线程准备获取锁，他不必去排队直接获取到，不会入队。获取不到才入队。

## notifyAll()
在面对公平锁和非公平锁的时候，效果一样。所有等待队列中的线程全部被唤醒，理论上是同时进入入口等待队列，等待时间是相同的。

## 锁的一些最佳实践
并发大师Doug Lea的最佳实践：  
* 永远只在更新对象的成员变量时加锁  
写属性的时候加锁，保证写的时候，别个不读。  
* 永远只在访问可变的成员变量时加锁  
读属性读时候加锁，保证读的时候，别个不能写，是准的数据。  
* 永远不在调用其他对象的方法时加锁   
因为调用其他对象的方法，实在是太不安全了，也许“其他”方法里面有线程sleep()的调用，也可能会有奇慢无比的I/O操作，这些都会严重影响性能。更可怕的是，“其他”类的方法可能也会加锁，然后双重加锁就可能导致死锁。  

## Condition
在使用Lock之前，使用最多的同步方式是synchronized关键字来实现同步方了。配合Object的wait()、notify()系列方法可以实现等待/通知模式。Condition接口也提供了类似Object的监视器方法，与Lock配合可以实现等待/通知模式。  
Condition对象是依赖于lock对象的，意思就是说condition对象需要通过lock对象进行创建出来  
Condition可以通俗的理解为条件队列。当一个线程在调用了await方法以后，直到线程等待的某个条件为真的时候才会被唤醒。这种方式为线程提供了更加简单的等待/通知模式。  
Condition必须要配合锁一起使用，因为对共享状态变量的访问发生在多线程环境下。一个Condition的实例必须与一个Lock绑定，因此Condition一般都是作为Lock的内部实现。  
Lock和Condition组合实现的管程，支持多个条件变量。    
* await() ：造成当前线程在接到信号或被中断之前一直处于等待状态。
* await(long time, TimeUnit unit) ：造成当前线程在接到信号、被中断或到达指定等待时间之前一直处于等待状态。
* awaitNanos(long nanosTimeout) ：造成当前线程在接到信号、被中断或到达指定等待时间之前一直处于等待状态。返回值表示剩余时间，如果在nanosTimesout之前唤醒，那么返回值 = nanosTimeout - 消耗时间，如果返回值 <= 0 ,则可以认定它已经超时了。
* awaitUninterruptibly() ：造成当前线程在接到信号之前一直处于等待状态。【注意：该方法对中断不敏感】。
* awaitUntil(Date deadline) ：造成当前线程在接到信号、被中断或到达指定最后期限之前一直处于等待状态。如果没有到指定时间就被通知，则返回true，否则表示到了指定时间，返回返回false。
* signal() ：唤醒一个等待线程。该线程从等待方法返回前必须获得与Condition相关的锁。
* signal()All ：唤醒所有等待线程。能够从等待方法返回的线程必须获得与Condition相关的锁。


## condition 实例代码
```java
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
```

## ReentrantLock 排他锁
可重入锁是一种排他锁，排他锁在同一个时刻只能够由一个线程进行访问。  
在ReentrantLock中使用一个int类型的state来表示同步状态，该值表示锁被一个线程重复获取的次数。
## ReentrantReadWriteLock
ReentrantReadWriteLock 读写锁维护了一对锁，一个读锁和一个写锁。通过分离读锁和写锁使得并发性相比一般的排他锁在性能上有更好的一些优势。

ReentrantReadWriteLock的特性
* 公平性选择：支持公平锁和非公平锁。
* 可重入性：支持可重入性。例读线程获取了读锁以后可以继续获取读锁。写线程获取写锁以后可以继续获取写锁。
* 锁降级：遵循获取读锁，获取写锁在释放写锁的次序。支持写锁降级为读锁。



- [link 2 github][0]  
- [link 2 csdn][1]

*******************
[0]: https://github.com/cmshome/x/tree/master/md
[1]: https://blog.csdn.net/qq_27093465