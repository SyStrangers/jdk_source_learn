package com.stranger.lang;

import javax.xml.crypto.Data;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.*;


public class LockDemo {

    public static void main(String[] args) throws InterruptedException {
        Main.main(args);
    }
}


class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) {
        lock.lock();
        try {
            //防止虚假唤醒，Condition的await调用一般会放在一个循环判断中
            while (count == items.length) {
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            notFull.signal();
            return x;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}


class RWTreeMap {
    private final Map<String, Data> m = new TreeMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Data get(String key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    public String[] allKeys() {
        r.lock();
        try {
            return m.keySet().toArray(new String[0]);
        } finally {
            r.unlock();
        }
    }

    public Data put(String key, Data value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }

}

class FIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
        Thread current = Thread.currentThread();
        waiters.add(current);

        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
        }
        waiters.remove();
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}

class MyThread extends Thread{
    private String name;
    private FIFOMutex mutex;
    public static int count;

    public MyThread(String name,FIFOMutex mutex){
        this.name = name;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            mutex.lock();
            count++;
            System.out.println("name:"+name+"count:"+count);
            mutex.unlock();
        }
    }
}

class Main{
    public static void main(String[] args)  throws InterruptedException{
        FIFOMutex mutex = new FIFOMutex();
        MyThread a1 = new MyThread("a1",mutex);
        MyThread a2 = new MyThread("a2",mutex);
        MyThread a3 = new MyThread("a3",mutex);
        a1.start();
        a2.start();
        a3.start();

        a1.join();
        a2.join();
        a3.join();
        assert MyThread.count  ==300;
        System.out.println("Finished");
    }
}



