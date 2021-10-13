package com.scaffold.algorithm.simple.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Karl on 2021/6/19
 **/
public class FindKSmallArray {
    public static void main(String[] args) {
        double result = 1.0 - 0.9;
        System.out.println("result = " + result);
        FindKSmallArray findKSmallArray = new FindKSmallArray();
        int k = 3;
        int[] input = findKSmallArray.randomArray(10);
        System.out.println("input = " + Arrays.toString(input));
        System.out.println("findWithArraySort = " + Arrays.toString(findKSmallArray.findWithArraySort(input,k)));
    }

    public int[] randomArray(int length) {
        if (length <= 0) {
            return null;
        }
        int[] result = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result[i] = random.nextInt(1000);
        }
        return result;
    }

    public int[] findWithArraySort(int[] arr, int k) {
        int[] result = new int[k];
        Arrays.sort(arr);
        for (int i = 0; i < k; i++) {
            result[i] = arr[i];
        }
        return result;

    }
}
