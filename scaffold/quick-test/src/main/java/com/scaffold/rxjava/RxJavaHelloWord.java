package com.scaffold.rxjava;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Arrays;

/**
 * Created by Karl on 2021/8/12
 **/
public class RxJavaHelloWord {
    public static void main(String[] args) {
        hello(new String[]{"Tom", "Jerry"});
    }

    public static void hello(String... names) {
        Flowable.fromArray(names).subscribe(s -> System.out.println("hello = " + s));
    }
}
