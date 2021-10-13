package com.scaffold.algorithm.hard;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 1+2+3-(4-(5-2))+2
 * 1. 使用数据结构栈
 * 2. 算法思路思考
 * {-1,+1}
 * 2.1 遍历字符串
 * 2.2 记录符号栈,最后一次压栈代表括号内使用的符号
 * 2.3 括号左边进入压栈,括号右边符号出栈
 * Created by Karl on 2021/7/19
 **/
public class SumExpression {
    public static void main(String[] args) {
        System.out.println(sumExpression("1+2 - ( 4 + 3-(5-4))"));
    }


    public static int sumExpression(String expression) {
        Deque<Integer> oprStack = new LinkedList<>();
        oprStack.push(1);

        int sign = 1;
        int result = 0;
        int i = 0;
        while (i < expression.length()) {
            final char c = expression.charAt(i);
            if (c == ' ') {
                i++;
            } else if (c == '(') {
                oprStack.push(sign);
                i++;
            } else if (c == '+') {
                sign = oprStack.peek();
                i++;
            } else if (c == '-') {
                sign = -oprStack.peek();
                i++;
            } else if (c == ')') {
                oprStack.pop();
                i++;
            } else {
                int num = 0;
                while (Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + expression.charAt(i) + '0';
                    i++;
                }
                result += sign * num;
            }
        }
        return result;
    }
}
