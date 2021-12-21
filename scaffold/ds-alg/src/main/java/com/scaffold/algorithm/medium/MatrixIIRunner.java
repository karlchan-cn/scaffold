package com.scaffold.algorithm.medium;

import java.util.Arrays;

/**
 * Created by Karl on 2021/8/3
 **/
public class MatrixIIRunner {
    public static void main(String[] args) {
        System.out.printf("result is :" + new MatrixIIRunner().searchMatrix(new int[][]{{1, 2}, {3, 5}, {4, 6}}, 11));
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        System.out.println("matrix = " + Arrays.deepToString(matrix) + ", target = " + target);
        boolean result = false;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }
        int minRow = 0;
        int maxCol = matrix[0].length - 1;
        int row = matrix.length - 1;
        int col = 0;
        // 树状遍历
        while (row >= minRow && col <= maxCol) {
            int k = matrix[row][col];
            if (k == target) {
                return true;
            }
            if (k > target) {
                row--;
            }
            if (k < target) {
                col++;
            }
        }
        return result;
    }
}
