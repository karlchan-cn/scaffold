package com.scaffold.bytecode;

/**
 * Created by Karl on 2022/11/15
 **/
public class ByteCodeDemo {
    private int a = 1;

    public int getA() {
        return a;
    }

    public ByteCodeDemo() {
    }

    public int add(ByteCodeDemo demo) {
        int pa = demo.getA();
        int b = 2;
        int c = a + b + pa;
        System.out.println(c);
        return c;
    }
}
