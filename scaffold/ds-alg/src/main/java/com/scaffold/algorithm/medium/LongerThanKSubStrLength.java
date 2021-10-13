package com.scaffold.algorithm.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karl on 2021/8/25
 **/
public class LongerThanKSubStrLength {

    public static void main(String[] args) {

    }
    public int longestSubstring(String s, int k) {
        if(s.length() < k ){
            return 0;
        }
        Map<Character,Integer> counter = new HashMap<Character,Integer>();
        for(int i = 0; i< s.length();i++){
            Character cs = s.charAt(i);
            counter.put(cs,counter.getOrDefault(cs,0) +1 );
        }
        int result = 0;
        for (Map.Entry<Character, Integer> item : counter.entrySet()) {
            if(item.getValue() < k) {
                final String[] split = s.split(String.valueOf(item.getKey()));
                for (String s1 : split) {
                    result = Math.max(result,longestSubstring(s1,k));
                }
                return result;
            }
        }
        return s.length();
    }
}
