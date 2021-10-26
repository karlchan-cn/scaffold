package com.scaffold.objectsize;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Karl on 2021/10/26
 **/
public class ArrayListSizeRunner {
    //private long age;
    //private long sex;
    //private long income;
    private Long incomeLong;
    private String name;
    private String country;
    //private boolean boolField;

    private long getAge() {
        return AGE_REF.longValue();
    }

    private String getName() {
        return NAME_REF;
    }

    private static Long AGE_REF = Long.valueOf(1);
    private static String NAME_REF = "NAME";

    /**
     * @param args
     */
    public static void main(String[] args) {
        final LinkedList<Object> ll = new LinkedList<>();
        final ArrayList<Object> al = new ArrayList<>(32);
        for (int i = 0; i < 32; i++) {
            final Object o = new Object();
            ll.add(o);
            al.add(o);
            printObjectSize(o);
        }
        printObjectSize(ll);
        printObjectSize(al);
        printObjectSize(new ArrayListSizeRunner());
    }

    public static void printObjectSize(Object object) {
        System.out.println("Object type: " + object.getClass() +
                ", size: " + com.util.objectsize.InstrumentationAgent.getObjectSize(object) + " bytes");
    }
}
