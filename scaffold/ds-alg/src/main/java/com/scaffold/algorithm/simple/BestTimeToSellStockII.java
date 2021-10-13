package com.scaffold.algorithm.simple;

/**
 * //https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
 * Created by Karl on 2021/6/25
 **/
public class BestTimeToSellStockII {
    public static void main(String[] args) {
        int[] prices = {7, 1, 7, 1, 4, 9};
        // 最佳卖出价是7
        System.out.printf("max profit is :" + new BestTimeToSellStockII().maxProfit(prices));
    }

    public int greedySolution(int[] prices) {
        return 0;
    }

    public int dpSulotion(int[] prices) {
        int length;
        if (prices == null || (length = prices.length) < 2) {
            return 0;
        }
        int[][] dp = new int[length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < length; i++) {
            //抛售股票
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            //持有股票
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[length - 1][0];
    }

    public int maxProfit(int[] prices) {
        int len;
        if (prices == null || (len = prices.length) < 2) {
            return 0;
        }
        // 0: 持有现金
        // 1: 持有股票
        // 状态转移
        int[][] dp = new int[len][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[len - 1][0];
    }
}
