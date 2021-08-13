package com.scaffold.algorithm.medium;

import java.util.Arrays;

/**
 * Created by Karl on 2021/8/3
 **/
public class MatrixIIRunner {
    public static void main(String[] args) {
        new MatrixIIRunner().searchMatrix(new int[][]{{1, 2}, {3, 5}}, 5);
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        System.out.println("matrix = " + Arrays.deepToString(matrix) + ", target = " + target);
        boolean result = false;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }
        int minRow = 0;
        int maxCol = matrix.length - 1;
        int startRow = matrix.length - 1;
        int startCol = 0;
        // 树状遍历
        while (startRow >= minRow || startCol <= maxCol) {

        }
        return result;
    }
}
