package com.scaffold.bytecode;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Karl on 2022/11/15
 **/
public class ASMByteCodeGenerator {
    public static void main(String[] args) throws IOException {
        // class reader and writer
        ClassReader reader = new ClassReader("com/scaffold/bytecode/Base");

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // process class
        ClassVisitor visitor = new MyClassVisitor(Opcodes.ASM9, writer);
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        byte[] data = writer.toByteArray();
        //输出
        File tmpF = new File("");
        File f = new File("scaffold/quick-test/target/classes/com/scaffold/bytecode/Base.class");
        try (FileOutputStream fout = new FileOutputStream(f)) {
            fout.write(data);
        }
        System.out.println("now generator cc success!!!!!");

    }
}
