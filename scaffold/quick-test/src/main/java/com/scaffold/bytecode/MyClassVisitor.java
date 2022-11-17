package com.scaffold.bytecode;

import org.objectweb.asm.*;

/**
 * Created by Karl on 2022/11/15
 **/
public class MyClassVisitor extends ClassVisitor implements Opcodes {
    protected MyClassVisitor(int api) {
        super(api);
    }

    protected MyClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        super.visit(i, i1, s, s1, s2, strings);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exception) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exception);
        if (!"<init>".equals(name) && methodVisitor != null) {
            methodVisitor = new MyMethodVisitor(methodVisitor);
        }
        return methodVisitor;

    }

    class MyMethodVisitor extends MethodVisitor implements Opcodes {
        public MyMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM9, methodVisitor);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("start");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode > Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
                // 方法返回之前打印“end”
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("end");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
            }
            mv.visitInsn(opcode);
        }
    }
}
