package com.scaffold.algorithm.simple;

/**
 * Created by Karl on 2021/7/21
 **/
public class DpCoinMatchSum {

    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        System.out.printf("min coins cout:%d", new DpCoinMatchSum().dp(coins, 15));
    }

    private int dp(int[] coins, int sum) {
        int[] dp = new int[sum + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = sum + 1;
        }
        //base case
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            //遍历硬币
            for (int j = 0; j < coins.length; j++) {
                //base case 当前硬币无法适配金额
                if (i - coins[j] < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], 1 + dp[i - coins[j]]);
            }
        }
        return (dp[sum] == sum + 1) ? -1 : dp[sum];
    }
}
