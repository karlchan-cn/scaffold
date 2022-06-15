package com.scaffold.highperformance;

import org.openjdk.jol.info.ClassData;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.Arrays;

/**
 * Created by Karl on 2022/1/12
 **/
public class ObjectSizeValidationRunner {
    /**
     * 引用类型对象
     */
    private static class RefPropertyObj {
        private Integer age;
        private Integer height;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }

    private static class PriPropertyObj {
        private int age;
        private int height;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static void main(String[] args) {
        // +UseCompressedOops=24bytes -UseCompressedOops=32
        System.out.println(ClassLayout.parseInstance(new RefPropertyObj()).toPrintable());
        // +UseCompressedOops=24bytes -UseCompressedOops=24
        System.out.println(GraphLayout.parseInstance(new RefPropertyObj()).toPrintable());
        System.out.println(ClassLayout.parseInstance(new PriPropertyObj()).toPrintable());

    }
}
