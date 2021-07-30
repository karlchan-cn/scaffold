package com.scaffold.algorithm.simple;

import java.util.Arrays;

/**
 * 删除数组重复数据
 * Created by Karl on 2021/7/22
 **/
public class DeleteDuplicatedElement {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2};
        System.out.printf("length:%d", removeDuplicates(nums));
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 1, 2};
        System.out.printf("length:%d", removeDuplicatesByTowPointer(nums));
        System.out.println(Arrays.toString(nums));
    }

    public static int removeDuplicates(int[] nums) {
        int maxVal = -1;
        int result = 0;
        int moveSize = 0;
        int cursor = 0;
        while (cursor < nums.length) {
            //当前值
            int val = nums[cursor];
            //当前值等于最大值,后续元素左移位数增加,当前下标+1
            if (val == maxVal) {
                moveSize++;
            } else {
                if (moveSize > 0) {
                    // 左移覆盖重复数据
                    nums[cursor - moveSize] = val;
                }
                maxVal = val;
                result++;
            }
            cursor++;
        }
        return result;
    }

    public static int removeDuplicatesByTowPointer(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int left = 0;
        int right = 1;
        for (; right < nums.length; right++) {
            if(nums[left] < nums[right]){
                nums[++left] = nums[right];
            }
        }
        return ++left;
    }

}
