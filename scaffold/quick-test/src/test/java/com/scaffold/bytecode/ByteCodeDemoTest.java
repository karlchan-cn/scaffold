package com.scaffold.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ByteCodeDemoTest {
    @Test
    void testAdd() {
        ByteCodeDemo byteCodeDemo = new ByteCodeDemo();
        assertEquals(4, byteCodeDemo.add(new ByteCodeDemo()));
    }
}

