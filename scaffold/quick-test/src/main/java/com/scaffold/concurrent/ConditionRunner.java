package com.scaffold.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Karl on 2021/12/29
 **/
public class ConditionRunner {
    private final static Lock lock = new ReentrantLock();
    private final static Condition conditionA = lock.newCondition();
    private final static Condition conditionB = lock.newCondition();


    public static void main(String[] args) {
        // 初始化实例
        ConditionRunner conditionDemo = new ConditionRunner();

        // 启动两个线程，分别调用各自的Condition的await方法令其等待
        new Thread(conditionDemo::awaitA).start();
        new Thread(conditionDemo::awaitB).start();

        // 主线程休眠2s后，先唤醒conditionA对应的线程
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conditionDemo.signalA();

        // 唤醒完conditionA对应的线程后，主线程再休眠2s，再唤醒conditionB对应的线程
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        conditionDemo.signalB();
    }

    private void signalA() {
        lock.lock();
        try {
            System.out.println("唤醒ConditionA对应的线程");
            conditionA.signal();
        } finally {
            lock.unlock();
        }
    }

    private void signalB() {
        lock.lock();
        try {
            System.out.println("唤醒ConditionB对应的线程");
            conditionB.signal();
        } finally {
            lock.unlock();
        }
    }

    private void awaitA() {
        lock.lock();
        try {
            System.out.println("ConditionA对应的线程等待...");
            try {
                conditionA.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    private void awaitB() {
        lock.lock();
        try {
            System.out.println("ConditionB对应的线程等待...");
            try {
                conditionB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }
}
