package com.scaffold.javap;

/**
 * Created by Karl on 2021/12/23
 **/
public class JavapDecompile {

    private String name;

    private Long age;

    public JavapDecompile(String name, Long age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "my name is: " + name + ". my age is:" + age;
    }

    /**
     * 歌唱.
     */
    public void sing() {
        System.out.println("I'm singing~");
    }

    public static void main(String[] args) {
        final JavapDecompile shady = new JavapDecompile("shady", 30L);
        shady.sing();
        System.out.println(shady);
    }

}
