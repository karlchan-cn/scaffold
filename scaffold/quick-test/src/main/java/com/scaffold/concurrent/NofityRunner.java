package com.scaffold.concurrent;

/**
 * Created by Karl on 2021/12/29
 **/
public class NofityRunner {
    private static final Object LOCK =  new Object();

    public void downloadIma() {
        System.out.println("开始下载图片");
        for (int i = 0; i < 101; i+=10) {
            System.out.println("down"+i+"%");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("download finished");
        synchronized (LOCK) {
            LOCK.notify();
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                System.out.println("download worker interrupted!");
            }
        }
        System.out.println("开始下载附件");
        for (int i = 0; i < 101; i+=10) {
            System.out.println("附件下载"+i+"%");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("附件下载成功");
    }
    public void reviewImage() {
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("wait for image downloaded~");
            }
            System.out.println("wake up to review image");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("图片展示完毕");

            // 图片展示线程展示完图片后，再通知下载线程下载附件
            LOCK.notify();
        }
    }

    public static void main(String[] args) {
        final NofityRunner nofityRunner = new NofityRunner();
        new Thread(() -> nofityRunner.downloadIma()).start();
        new Thread(() -> nofityRunner.reviewImage()).start();
    }
}
