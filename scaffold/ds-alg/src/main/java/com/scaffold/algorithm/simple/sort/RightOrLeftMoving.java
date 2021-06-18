package com.scaffold.algorithm.simple.sort;

/**
 * Created by Karl on 2021/6/21
 **/
public class RightOrLeftMoving {
    public static void main(String[] args) {
        bitRevsese();
    }
    public static void bitRevsese(){
        int a= 1 << 2;
        System.out.println(a);
        System.out.println(Integer.toBinaryString(~a));
        System.out.println(Long.parseLong("C0000000",16));
        System.out.println(Math.log(Long.parseLong("C0000000",16))/Math.log(2));
        System.out.println(Math.log(32768)/Math.log(2));
        System.out.println(Math.log(16384)/Math.log(2));
        int hashCode = new RightOrLeftMoving().hashCode();
        System.out.println(Integer.toBinaryString(hashCode));
        System.out.println(Integer.toBinaryString(hashCode >>> 16 ));
        System.out.println(Integer.toBinaryString(hashCode >> 16 ));
        System.out.println(Integer.toBinaryString(hashCode ^ (hashCode >>> 16) ));

    }
}

