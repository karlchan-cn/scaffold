package com.scaffold.alg.lintcode.bloomberg;


import java.util.Comparator;
import java.util.List;

/**
 * Created by Karl on 2022/11/2
 **/
public class InterviewCost {



    public int getMinCost(List<List<Integer>> cost) {
        cost.sort(Comparator.comparingInt(t0 -> (t0.get(0) - t0.get(1))));
        int subIndex = cost.size() / 2;
        int result = 0;
        for (int i = 0; i < subIndex; i++) {
            result += (cost.get(i).get(0) + cost.get(i + subIndex).get(1));
        }
        return result;
    }
}
