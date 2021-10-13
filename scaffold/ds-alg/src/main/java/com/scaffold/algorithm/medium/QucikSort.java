package com.scaffold.algorithm.medium;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;

/**
 * 快速排序.
 * Created by Karl on 2021/6/14
 **/
public class QucikSort {
    public static void main(String[] args) {
        int[] array = {1, 5, 9, 110, 2, 4, 102};
        new QucikSort().randomizeQuicksortCopied(array, 0, array.length - 1);
        System.out.printf(Arrays.toString(array));
    }

    public int[] sortArray(int[] nums) {
        randomizedQuicksort(nums, 0, nums.length - 1);
        return nums;
    }

    public void randomizeQuicksortCopied(int[] param, int l, int r) {
        if (l < r) {
            int pos = randomizedPartitionCopied(param, l, r);
            randomizeQuicksortCopied(param, l, pos - 1);
            randomizeQuicksortCopied(param, pos
                    + 1, r);
        }
    }

    public int randomizedPartitionCopied(int[] param, int l, int r) {
        int i = new Random().nextInt(r - l + 1) + l;
        swapCopied(param, r, i);
        return partitionCopied(param, l, r);
    }

    public void swapCopied(int[] param, int i, int j) {
        int tmp = param[i];
        param[i] = param[j];
        param[j] = tmp;

    }

    public void randomizedQuicksort(int[] nums, int l, int r) {
        if (l < r) {
            int pos = randomizedPartition(nums, l, r);
            randomizedQuicksort(nums, l, pos - 1);
            randomizedQuicksort(nums, pos + 1, r);
        }
    }

    public int randomizedPartition(int[] nums, int l, int r) {
        int i = new Random().nextInt(r - l + 1) + l; // 随机选一个作为我们的主元
        swap(nums, r, i);
        return partition(nums, l, r);
    }

    public int partitionCopied(int[] param, int l, int r) {
        int pivot = param[r];
        int i = l - 1;
        for (int j = l; j <= r - 1; ++j) {
            // 小于等于轴值
            if (param[j] <= pivot) {
                i = i + 1;
                swapCopied(param, i, j);
            }
        }
        swapCopied(param, i + 1, r);
        return i + 1;
    }

    public int partition(int[] nums, int l, int r) {
        int pivot = nums[r];
        int i = l - 1;
        for (int j = l; j <= r - 1; ++j) {
            if (nums[j] <= pivot) {
                i = i + 1;
                swap(nums, i, j);
            }
        }
        swap(nums, i + 1, r);
        return i + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
