package com.scaffold.algorithm.bd.string;

/**
 * Created by Karl on 2021/11/6
 **/
public class LongPrefixString {
    private static final String EMPTY = "";

    public static void main(String[] args) {

    }

    /**
     *
     * @param strings
     * @return
     */
    public String sulution2(String[] strings) {
        if (strings == null && strings.length <= 0) {
            return EMPTY;
        }
        int length = strings[0].length();
        for (int i = 0; i < length; i++) {
            char c = strings[0].charAt(i);
            for (int j = 1; j < strings.length; j++) {
                if (i >= strings[j].length() || strings[j].charAt(i) != c) {
                    return strings[0].substring(0, i);
                }
            }
        }
        return strings[0];
    }

    /**
     * LCP(LCP(LCP(S1,S2),S3),…Sn)
     *
     * @param strings
     * @return
     */
    public String solution1(String[] strings) {
        if (strings == null && strings.length <= 0) {
            return EMPTY;
        }
        String prefix = strings[0];
        for (int i = 1; i < strings.length; i++) {
            prefix = lcp(prefix, strings[i]);
            if (prefix.length() == 0) {
                break;
            }
        }
        return prefix;
    }

    private String lcp(String f, String s) {
        int length = Math.min(f.length(), s.length());
        int index = 0;
        while (index < length && f.charAt(index) == s.charAt(index)) {
            index++;
        }
        return f.substring(0, index);
    }

}
