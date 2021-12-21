package com.scaffold.algorithm.bd.string;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Karl on 2021/10/27
 **/
public class LongDedupliatedSubStr {
    public static void main(String[] args) {
        System.out.println("result (abcad)= " + new LongDedupliatedSubStr().result("abcad"));

    }

    public int result(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Set<Character> ds = new HashSet<>();
        int result = 0;
        int rk = -1;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            //左指针右移一位
            if (i > 0) {
                ds.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !ds.contains(s.charAt((rk + 1)))) {
                ds.add(s.charAt(rk + 1));
                ++rk;
            }
            //
            result = Math.max(result, rk - i + 1);
        }
        return result;
    }
}
