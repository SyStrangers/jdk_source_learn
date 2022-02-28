package com.stranger.lang.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionObjectDemo {
    static ReentrantLock lock = new ReentrantLock();
    static Condition con = lock.newCondition();

    public static void main(String[] args) {
        Thread a = new Thread(()->{
            lock.lock();
            try{
                con.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        },"AA");
        a.start();
    }

}
