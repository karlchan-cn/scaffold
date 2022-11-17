package com.scaffold.bytecode;

public class ReflectionDemo {

    public static void main(String[] args) throws Exception {
        doRegular();
        doReflection();
    }

    public static void doRegular() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ByteCodeDemo a = new ByteCodeDemo();
            a.getA();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void doReflection() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ByteCodeDemo a = (ByteCodeDemo) Class.forName("com.scaffold.bytecode.ByteCodeDemo").newInstance();
            a.getA();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}