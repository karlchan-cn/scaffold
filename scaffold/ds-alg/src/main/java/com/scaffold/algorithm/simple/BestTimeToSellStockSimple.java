package com.scaffold.algorithm.simple;

/**
 * Created by Karl on 2021/6/25
 **/
public class BestTimeToSellStockSimple {
    public static void main(String[] args) {
        int[] prices = {7, 1, 4, 2, 1, 8};
        // 最佳卖出价是7
        System.out.printf("max profit is :" + new BestTimeToSellStockSimple().maxProfit(prices));
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int minPriceToBuy = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            int price = prices[i];
            if (price < minPriceToBuy) {
                // 不能卖,找到低点
                minPriceToBuy = price;
            } else if (price - minPriceToBuy > maxProfit) {
                // 当天卖出是最高价
                maxProfit = price - minPriceToBuy;
            }
        }
        return maxProfit;
    }
}
