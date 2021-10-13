package com.scaffold.algorithm.simple.sort;

import com.scaffold.algorithm.medium.QucikSort;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Karl on 2021/6/24
 **/
public class QuickSortImpl {
    public static void main(String[] args) {
        int arr1[] = {1,10,2,7,3,0,15,17,16};
        QuickSortImpl quickSort = new QuickSortImpl();
        System.out.println("before sorted = " + Arrays.toString(arr1));
        quickSort.randomQuickSort(arr1, 0, arr1.length - 1);
        System.out.println("after sorted = " + Arrays.toString(arr1));
    }
    public void randomQuickSort(int[] nums, int start, int end) {
        if (start < end) {
            int partionIdx = partition(nums, start, end);
            randomQuickSort(nums, start, partionIdx - 1);
            randomQuickSort(nums, partionIdx + 1, end);
        }
    }

    private int partition(int[] nums, int start, int end) {
        int randomIdx = new Random().nextInt(end - start + 1) + start;
        swap(nums, end, randomIdx);
        int pivot = nums[end];
        int i = start - 1;
        // j<=end -1 不包括轴下标
        for (int j = start; j <= end - 1; j++) {
            if (nums[j] <= pivot) {
                i++;
                // 将小于pivot的值放到左边
                swap(nums, j, i);
            }
        }
        swap(nums, i + 1, end);
        return i + 1;
    }

    /**
     * 交换函数
     *
     * @param nums 数字里诶包
     * @param from from索引
     * @param to
     */
    private void swap(int[] nums, int from, int to) {
        int tmp = nums[to];
        nums[to] = nums[from];
        nums[from] = tmp;
    }
}
