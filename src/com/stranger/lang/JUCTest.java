package com.stranger.lang;

import java.util.concurrent.locks.ReentrantLock;

public class JUCTest {

    static int end = 12;

    public static void main(String[] args) {
        for (int i = 0; i < end; i++) {
            new ReentrantLockDemo().start();
        }
    }

    static class ReentrantLockDemo extends Thread {

        ReentrantLock reentrantLock = new ReentrantLock();

        private static int j = 11;

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                if (j == 0) {
                    System.out.println("当前线程结束");
                } else {
                    j--;
                    System.out.println("当前线程正在运行 j = " + j);
                }
            } catch (Exception e) {

            } finally {
                reentrantLock.unlock();
            }
        }
    }
}
