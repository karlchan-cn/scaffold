package com.scaffold.objectsize;

import org.openjdk.jol.info.ClassLayout;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * vm args:
 * -javaagent:"D:\Users\Documents\GitHub\ObjectSizeFetcher\target\object-sizer-1.0-SNAPSHOT.jar"
 * -XX:+/-UseCompressedOops
 * -XX:+PrintFlagsFinal
 * Created by Karl on 2021/10/26
 **/
public class ArrayListSizeRunner {
    //private long age;
    //private long sex;
    //private long income;
    private Long incomeLong;
    private String name;
    private String country;
    private long longField;
    //private long age;
    //private long fakeAge;
    //private long ecpm3;
    //private boolean boolField;

    private long getAge() {
        return AGE_REF.longValue();
    }

    private String getName() {
        return NAME_REF;
    }

    private static Long AGE_REF = Long.valueOf(1);

    private static String NAME_REF = "NAME";

    static class LinkeNode {
        private LinkeNode next;
        private Object val;
        LinkeNode(Object val){
            this.val = val;
        }


    }

    /**
     * @param args
     */

    public static void main(String[] args) {
        if (null instanceof String) {
            System.out.println("args = " + Arrays.deepToString(args));
        }
        final LinkedList<Object> ll = new LinkedList<>();
        Object[] elementData = new Object[100_000];
        final ArrayList<Object> al = new ArrayList<>(1000_00);
        for (int i = 0; i < 32; i++) {
            final Object o = new Object();
            ll.add(o);
            al.add(o);
            printObjectSize(o);
        }
        printObjectSize(ll);
        printObjectSize(al);
        final ArrayListSizeRunner arrayListSizeRunner = new ArrayListSizeRunner();
        printObjectSize(arrayListSizeRunner);
        System.out.println("array list size");
        printObjectSize(elementData);
        printObjectSize(new Object[100_000]);
        printObjectSize(Long.valueOf(10L));
        Object val = new Object();
        printObjectSize(new LinkeNode(val));
        printObjectSize(val);
        arrayListSizeRunner.incomeLong =  Long.valueOf(200000);
        arrayListSizeRunner.hashCode();
        arrayListSizeRunner.incomeLong.hashCode();
        final Long anotherLong = Long.valueOf(200000);
        System.out.println(ClassLayout.parseInstance(arrayListSizeRunner).toPrintable());
        System.out.println(ClassLayout.parseInstance(arrayListSizeRunner.incomeLong).toPrintable());
        System.out.println("print Long info:");
        System.out.println(ClassLayout.parseInstance(anotherLong).toPrintable());
        System.out.println("print Object Array info:");
        Object[] objectArray =  new Object[4];
        for (int i = 0; i < 4; i++) {
            objectArray[i] = new ArrayListSizeRunner();
        }
        System.out.println(ClassLayout.parseInstance(objectArray).toPrintable());
        printObjectSize(objectArray);
        System.out.println("print Array List info:");
        ArrayList arrayList =  new ArrayList(4);
        for (int i = 0; i < 4; i++) {
            arrayList.add(i);
        }
        System.out.println(ClassLayout.parseInstance(arrayList).toPrintable());
        printObjectSize(arrayList);
        System.out.println("print Object Array info:");
        System.out.println(ClassLayout.parseInstance(arrayListSizeRunner).toPrintable());
        arrayListSizeRunner.trySyncLock();
        System.gc();

        PhantomReference<Object> ref = new PhantomReference<>(new Object(),null);
        System.out.println(ClassLayout.parseInstance(ref).toPrintable());
    }

    public synchronized void trySyncLock() {
        System.out.println(ClassLayout.parseInstance(this).toPrintable());
        tryBiasied();
    }
    public synchronized void tryBiasied() {
        System.out.println(ClassLayout.parseInstance(this).toPrintable());
    }
    public static void printObjectSize(Object object) {
        System.out.println("Object type: " + object.getClass() +
                ", size: " + com.util.objectsize.InstrumentationAgent.getObjectSize(object) + " bytes");
    }
}

