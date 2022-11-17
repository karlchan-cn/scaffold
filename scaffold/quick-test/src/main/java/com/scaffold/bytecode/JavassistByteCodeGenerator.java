package com.scaffold.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
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
public class JavassistByteCodeGenerator {
    public static void main(String[] args) throws IOException, NotFoundException {
        // class reader and writer
        ClassReader reader = new ClassReader("com/scaffold/bytecode/Base");

        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("com.scaffold.bytecode.JavassitBase");
        CtMethod m = cc.getDeclaredMethod("process");


    }
}
