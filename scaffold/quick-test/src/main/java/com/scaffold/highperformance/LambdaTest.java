package com.scaffold.highperformance;

public class LambdaTest {
    @FunctionalInterface
    public interface LambdaDemo {
        public void runLambda();
    }

    public static void doSomething(LambdaDemo demo) {
        demo.runLambda();
    }

    public static void main(String[] args) {
        doSomething(() -> System.out.println("hello world!"));
    }
}