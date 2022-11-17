package com.scaffold.algorithm.dp;

/**
 * Created by Karl on 2022/11/14
 **/
public class Fibonacci {

    public static void main(String[] args) {
        final Fibonacci fib = new Fibonacci();
        long start = System.currentTimeMillis();
        int executeCount = 40;
        System.out.println("recursion(" + executeCount + ") result: " + fib.recursion(executeCount) + " cost(ms) " + (System.currentTimeMillis() - start));
        Fibonacci dpFib = new Fibonacci(executeCount);
        start = System.nanoTime();
        System.out.println("dpRecursion(" + executeCount + ") result: " + dpFib.dpRecursion(executeCount) + " cost(ns) " + (System.nanoTime() - start));
        start = System.nanoTime();
        System.out.println("dp(" + executeCount + ") result: " + dpFib.dp(executeCount) + " cost(ns) " + (System.nanoTime() - start));
    }

    /**
     * 避免递归stack调用
     *
     * @param n
     * @return
     */
    public int dp(int n) {
        int dp[] = new int[n];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n - 1];
    }

    /**
     * stack + 重复计算
     *
     * @param n
     * @return
     */
    public int recursion(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return recursion(n - 1) + recursion(n - 2);
    }

    private int[] dpTable;

    public Fibonacci() {

    }

    public Fibonacci(int tableNum) {
        dpTable = new int[tableNum];
        dpTable[0] = 1;
        dpTable[1] = 1;
    }

    /**
     * 自顶而下的递归调用
     *
     * @param n
     * @return
     */
    public int dpRecursion(int n) {
        if (dpTable == null) {
            return 0;
        }
        int ret = dpTable[n - 1];
        if (ret > 0) {
            return ret;
        }
        ret = dpRecursion(n - 1) + dpRecursion(n - 2);
        dpTable[n - 1] = ret;

        return ret;
    }
}
