package com.stranger.lang;

import java.util.concurrent.locks.ReentrantLock;

public class ReentranLockDemo {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread0 = lockThread();
        thread0.start();
        Thread thread1 = lockThread();
        thread1.start();
    }

    private static  Thread lockThread(){
        return new Thread(()->{
            while (true){
                System.out.println("before lock:"+Thread.currentThread().getName());

                lock.lock();
                System.out.println("in lock:"+Thread.currentThread().getName());
                sleep();

                lock.unlock();
                System.out.println("after lock:"+Thread.currentThread().getName());
            }
        });
    }


    private static void sleep(){
        try{
            Thread.sleep(3000L);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
