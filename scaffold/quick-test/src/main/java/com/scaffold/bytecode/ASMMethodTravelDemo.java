package com.scaffold.bytecode;

import com.scaffold.bytecode.analysis.NullDereferenceAnalyzer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ListIterator;

/**
 * Created by Karl on 2022/11/16
 **/
public class ASMMethodTravelDemo {
    public static void main(String[] args) throws IOException, AnalyzerException {
        // class reader and writer
        ClassReader reader = new ClassReader("com/scaffold/bytecode/ByteCodeDemo");
        ClassNode cn = new ClassNode();
        reader.accept(cn, ClassReader.SKIP_FRAMES);
        reader.accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);

        final NullDereferenceAnalyzer analyzer = new NullDereferenceAnalyzer();

        for (MethodNode m : cn.methods) {

            System.out.println("method name = " + m.name);

            if (!"add".equals(m.name)) {
                continue;
            }
            analyzer.findNullDereferences(Type.getObjectType("com/scaffold/bytecode/ByteCodeDemo").getInternalName(),
                    m);

            final ListIterator<AbstractInsnNode> it = m.instructions.iterator();
            System.out.println("instructions count = " + m.instructions.size());
            while (it.hasNext()) {
                final AbstractInsnNode next = it.next();
                if (!(next instanceof MethodInsnNode)) {
                    continue;
                }
                MethodInsnNode in = (MethodInsnNode) next;

                System.out.println("opcode:" + in.getOpcode() +
                        " type:" + in.getType() + " name" + in.name + " to String:" + in);
            }
        }
    }
}
