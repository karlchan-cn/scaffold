package com.scaffold.reordering;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 可能结果:
 * <li>(0,1)</li>
 * <li>(1,0)</li>
 * <li>(1,1)</li>
 * <li>(0,0)</li>
 * Created by Karl on 2021/12/29
 **/
public class PossibleReordering {
    static int x = 0, y = 0;
    static int a = 0, b = 0;
    static final Set<String> RESULT_SET = new HashSet<>();

    public static void main(String[] args) throws InterruptedException {
        final PossibleReordering possibleReordering = new PossibleReordering();
        for (int i = 0; i < 10000_000; i++) {
            possibleReordering.redorderingDemo();
            possibleReordering.reset();
        }
        System.out.println("all result : ");
        RESULT_SET.forEach((String e) -> System.out.println("result: " + e));

    }

    private void reset() {
        x = 0;
        y = 0;
        a = 0;
        b = 0;
    }

    private void redorderingDemo() throws InterruptedException {
        Thread one = new Thread(() -> {
            a = 1;
            x = b;
        });

        Thread other = new Thread(() -> {
            b = 1;
            y = a;
        });
        one.start();
        other.start();

        // wait for thread to die
        one.join();
        other.join();
        final String result = "(" + x + "," + y + ")";
        if (RESULT_SET.add(result)) {
            System.out.println(result);
        }

    }
}
