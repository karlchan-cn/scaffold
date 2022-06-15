package com.scaffold.http.get;

import java.util.Arrays;

/**
 * Created by Karl on 2021/12/30
 **/
public class BitRunner {
    public void printNegativeBinary() {
        System.out.println("-3's hex:" + Integer.toHexString(-3));
        System.out.println("-3's hinary:" + Integer.toHexString(-3));
        System.out.println("int's max value:" + Integer.MAX_VALUE);
        System.out.println("int's min value:" + Integer.MIN_VALUE);
    }


    public void rightMove() {
        System.out.println("-127's binary:" + Integer.toBinaryString(-127));
        System.out.println("~127's ^:" + Integer.toBinaryString(~127));
        System.out.println("-127 >> 2:" + (-127 >> 2));
        System.out.println("-127 >> 2:" + Integer.toBinaryString(-127 >> 2));
    }

    public void printBitReverse() {
        System.out.println("123's binary:" + Integer.toBinaryString(123));
        System.out.println("~123's ^:" + Integer.toBinaryString(~123));
        System.out.println("1's binary:" + Integer.toBinaryString(1));
        System.out.println("1's ^:" + Integer.toBinaryString(~1));
    }

    public static void main(String[] args) {
        System.out.println("1000 = " + Integer.parseInt("10000000000000000000000000000000", 2));
        System.out.println("MAX_VALUE = " + Integer.MAX_VALUE);
        final BitRunner br = new BitRunner();
        br.printNegativeBinary();
        br.printBitReverse();
        br.rightMove();
    }
}
