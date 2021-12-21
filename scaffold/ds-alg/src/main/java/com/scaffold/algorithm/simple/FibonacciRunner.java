package com.scaffold.algorithm.simple;

import java.util.Arrays;

/**
 * Created by Karl on 2021/7/12
 **/
public class FibonacciRunner {
    /**
     * @param n
     * @return
     */
    public static final int dpFibonacci(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] memory = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            memory[i] = -1;
        }
        return innerDpFibonacci(n, memory);
    }

    private static final int innerDpFibonacci(int n, int[] memory) {
        if (memory[n] != -1) {
            return memory[n];
        }
        if (n <= 2) {
            memory[n] = 1;
        } else {
            memory[n] = innerDpFibonacci(n - 1, memory) + innerDpFibonacci(n - 2, memory);
        }
        return memory[n];
    }

    /**
     * @param n
     * @return
     */
    public static final int fibonacci(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void main(String[] args) {
        int i = 10;
        int[] result = new int[i];
        for (int idx = 0; idx < i; idx++) {
            result[idx] = fibonacci(idx);
        }
        System.out.println(Arrays.toString(result));
        for (int idx = 0; idx < i; idx++) {
            result[idx] = dpFibonacci(idx);
        }
        System.out.println(Arrays.toString(result));
    }
}
