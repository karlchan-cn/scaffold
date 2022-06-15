package com.scaffold.highperformance;

/**
 * Created by Karl on 2022/1/10
 **/
public class HelloWorldClass {
    public void sayHelloWorld() {
        System.out.println("hello, world.");
    }

    protected int protectedAdd(int a, int b) {
        privateAdd(a, b);
        return a + b;
    }

    void spin() {
        int i;
        for (i = 0; i < 100; i++) {
            ;
        }
    }

    private int privateAdd(int a, int b) {
        return a + b;
    }

    public void nlp(Object obj) {
        int sum = 0;
        for (int i = 0; i < 200; i++) {
            sum += i;
        }
    }

    public static void main(String[] args) {
        final HelloWorldClass helloWorldClass = new HelloWorldClass();
        helloWorldClass.sayHelloWorld();
        helloWorldClass.protectedAdd(1, 2);
        System.out.println(helloWorldClass.privateAdd(3, 4));
        helloWorldClass.spin();
    }
}
