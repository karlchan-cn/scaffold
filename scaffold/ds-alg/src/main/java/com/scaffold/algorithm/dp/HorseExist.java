package com.scaffold.algorithm.dp;

import java.util.Arrays;

public class HorseExist {
    public static void main(String[] args) {
        final HorseExist he = new HorseExist();
        System.out.println(he.knightProbability(3,2,0,0));
        System.out.println(he.knightProbability2(3,2,0,0));
        System.out.println(he.knightProbability3(3,2,0,0));

    }
    /**
     * @param n: int
     * @param k: int
     * @param r: int
     * @param c: int
     * @return: the probability
     */
    static int[][] dirs = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}};
    public double knightProbability(int n, int k, int r, int c) {
        // Write your code here.
        double[][][] dp = new double[k + 1][n][n];
        for (int step = 0; step <= k; step++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (step == 0) {
                        dp[step][i][j] = 1;
                    } else {
                        for (int[] dir : dirs) {
                            int ni = i + dir[0], nj = j + dir[1];
                            if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                                dp[step][i][j] += dp[step - 1][ni][nj] / 8;
                            }
                        }
                    }
                }
            }
        }
        return dp[k][r][c];
    }

   // int[][] dirs = {{-2,-1},{-2,1},{2,-1},{2,1},{-1,2},{1,2},{-1,-2},{1,-2}};
    /**
     * @param n: int
     * @param k: int
     * @param r: int
     * @param c: int
     * @return: the probability
     */
    public double knightProbability2(int n, int k, int r, int c) {
        double[][][] dp = new double[k+1][n][n];
        // current position exist 100%
        dp[0][r][c] = 1;
        for(int step = 1; step <= k; step++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    for(int[] dir : dirs){
                        // 新坐标
                        int ti = i + dir[0];
                        int tj = j + dir[1];
                        // out of n*n
                        if(ti < 0 || ti >= n  || tj < 0 || tj >= n ) {
                            continue;
                        }
                        dp[step][i][j] += dp[step-1][ti][tj] * 0.125;
                    }
                }
            }
        }
        double result =  0.0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++){
                result += dp[k][i][j];
            }
        }
        return result;
    }

    int[] dirx = {1, 1, 2, 2, -1, -1, -2, -2};
    int[] diry = {2, -2, 1, -1, 2, -2, 1, -1};
    int n;

    public double knightProbability3(int n, int k, int r, int c) {
        // Write your code here.
        this.n = n;

        // 状态：第k步走到坐标（i，j）的概率
        double[][][] dp = new double[n][n][k + 1];
        dp[r][c][0] = 1;

        // 从1走到k步
        for (int step = 1; step <= k; step++) {

            // 每一步都计算走到每个坐标的概率，依赖跟步数有关，跟要走的坐标没有重叠，所以不用复制棋盘
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    // 查看八个方向
                    for (int d = 0; d < 8; d++) {
                        if (!isValid(i, j, d)) {
                            continue;
                        }
                        dp[i][j][step] += dp[i - dirx[d]][j - diry[d]][step - 1] * (1.0 / 8);
                    }
                }
            }
        }

        double sum = 0.0;
        // 最后将所有位置相加一遍，就是留在棋盘上的概率
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum += dp[i][j][k];
            }
        }
        return sum;
    }

    private boolean isValid(int x, int y, int d) {
        // System.out.print(d + " ");
        if (x - dirx[d] >= n || x - dirx[d] < 0) {
            return false;
        }
        return y - diry[d] < n && y - diry[d] >= 0;
    }
}