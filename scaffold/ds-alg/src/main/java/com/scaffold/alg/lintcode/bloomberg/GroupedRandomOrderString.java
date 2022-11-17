package com.scaffold.alg.lintcode.bloomberg;

import java.util.*;

/**
 * Created by Karl on 2022/11/8
 **/
public class GroupedRandomOrderString {
    /**
     * @param strs: the given array of strings
     * @return: The anagrams which have been divided into groups
     * we will sort your return value in output
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        // write your code here
        Map<String, List<String>> resultMap = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedStr = new String(chars);
            List<String> values = resultMap.get(sortedStr);
            if (values == null) {
                values = new ArrayList<>();
                resultMap.put(sortedStr, values);
            }
            values.add(str);
        }
        return new ArrayList<>(resultMap.values());
    }
}
