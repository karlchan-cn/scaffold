package com.scaffold.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.CheckMethodAdapter;
import org.objectweb.asm.util.TraceMethodVisitor;

class MyClassVisitorTest {
    @Test
    void testMyMethodVisitorConstructor() {
        MyClassVisitor myClassVisitor = null;
        MethodNode methodNode = new MethodNode();
        assertSame(methodNode, (myClassVisitor.new MyMethodVisitor(methodNode)).getDelegate());
    }

    @Test
    void testMyMethodVisitorVisitCode() {
        MyClassVisitor myClassVisitor = new MyClassVisitor(Opcodes.ASM9);
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(new MethodNode());
        myMethodVisitor.visitCode();
        InsnList insnList = ((MethodNode) myMethodVisitor.getDelegate()).instructions;
        assertEquals(3, insnList.size());
        AbstractInsnNode first = insnList.getFirst();
        assertTrue(first instanceof FieldInsnNode);
        AbstractInsnNode last = insnList.getLast();
        assertTrue(last instanceof MethodInsnNode);
        assertEquals("java/io/PrintStream", ((MethodInsnNode) last).owner);
        assertEquals("println", ((MethodInsnNode) last).name);
        assertFalse(((MethodInsnNode) last).itf);
        assertEquals("(Ljava/lang/String;)V", ((MethodInsnNode) last).desc);
        AbstractInsnNode previous = last.getPrevious();
        assertTrue(previous instanceof LdcInsnNode);
        assertEquals(182, last.getOpcode());
        assertSame(previous, first.getNext());
        assertEquals("out", ((FieldInsnNode) first).name);
        assertEquals("java/lang/System", ((FieldInsnNode) first).owner);
        assertEquals(178, first.getOpcode());
        assertEquals("Ljava/io/PrintStream;", ((FieldInsnNode) first).desc);
        assertSame(last, previous.getNext());
        assertEquals(18, previous.getOpcode());
        assertEquals("start", ((LdcInsnNode) previous).cst);
        assertSame(first, previous.getPrevious());
    }

    @Test
    void testMyMethodVisitorVisitCode2() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new CheckMethodAdapter(new MethodNode()));
        myMethodVisitor.visitCode();
        InsnList insnList = ((MethodNode) myMethodVisitor.getDelegate().getDelegate()).instructions;
        assertEquals(3, insnList.size());
        AbstractInsnNode first = insnList.getFirst();
        assertTrue(first instanceof FieldInsnNode);
        AbstractInsnNode last = insnList.getLast();
        assertTrue(last instanceof MethodInsnNode);
        assertEquals("java/io/PrintStream", ((MethodInsnNode) last).owner);
        assertEquals("println", ((MethodInsnNode) last).name);
        assertFalse(((MethodInsnNode) last).itf);
        assertEquals("(Ljava/lang/String;)V", ((MethodInsnNode) last).desc);
        AbstractInsnNode previous = last.getPrevious();
        assertTrue(previous instanceof LdcInsnNode);
        assertEquals(182, last.getOpcode());
        assertSame(previous, first.getNext());
        assertEquals("out", ((FieldInsnNode) first).name);
        assertEquals("java/lang/System", ((FieldInsnNode) first).owner);
        assertEquals(178, first.getOpcode());
        assertEquals("Ljava/io/PrintStream;", ((FieldInsnNode) first).desc);
        assertSame(last, previous.getNext());
        assertEquals(18, previous.getOpcode());
        assertEquals("start", ((LdcInsnNode) previous).cst);
        assertSame(first, previous.getPrevious());
    }

    @Test
    void testMyMethodVisitorVisitCode3() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new CheckMethodAdapter(new CheckMethodAdapter(new MethodNode())));
        myMethodVisitor.visitCode();
        InsnList insnList = ((MethodNode) myMethodVisitor.getDelegate().getDelegate().getDelegate()).instructions;
        assertEquals(3, insnList.size());
        AbstractInsnNode first = insnList.getFirst();
        assertTrue(first instanceof FieldInsnNode);
        AbstractInsnNode last = insnList.getLast();
        assertTrue(last instanceof MethodInsnNode);
        assertEquals("java/io/PrintStream", ((MethodInsnNode) last).owner);
        assertEquals("println", ((MethodInsnNode) last).name);
        assertFalse(((MethodInsnNode) last).itf);
        assertEquals("(Ljava/lang/String;)V", ((MethodInsnNode) last).desc);
        AbstractInsnNode previous = last.getPrevious();
        assertTrue(previous instanceof LdcInsnNode);
        assertEquals(182, last.getOpcode());
        assertSame(previous, first.getNext());
        assertEquals("out", ((FieldInsnNode) first).name);
        assertEquals("java/lang/System", ((FieldInsnNode) first).owner);
        assertEquals(178, first.getOpcode());
        assertEquals("Ljava/io/PrintStream;", ((FieldInsnNode) first).desc);
        assertSame(last, previous.getNext());
        assertEquals(18, previous.getOpcode());
        assertEquals("start", ((LdcInsnNode) previous).cst);
        assertSame(first, previous.getPrevious());
    }

    @Test
    void testMyMethodVisitorVisitCode4() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new TraceMethodVisitor(new ASMifier()));
        myMethodVisitor.visitCode();
        assertEquals(4, ((TraceMethodVisitor) myMethodVisitor.getDelegate()).p.getText().size());
    }

    @Test
    void testMyMethodVisitorVisitCode5() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by visitCode()
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        MyClassVisitor myClassVisitor = null;
        (myClassVisitor.new MyMethodVisitor(new CheckMethodAdapter(null))).visitCode();
    }

    @Test
    void testMyMethodVisitorVisitInsn() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(new MethodNode());
        myMethodVisitor.visitInsn(1);
        InsnList insnList = ((MethodNode) myMethodVisitor.getDelegate()).instructions;
        assertEquals(1, insnList.size());
        AbstractInsnNode first = insnList.getFirst();
        assertTrue(first instanceof org.objectweb.asm.tree.InsnNode);
        assertTrue(insnList.getLast() instanceof org.objectweb.asm.tree.InsnNode);
        assertEquals(1, first.getOpcode());
    }

    @Test
    void testMyMethodVisitorVisitInsn2() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(new MethodNode());
        myMethodVisitor.visitInsn(191);
        InsnList insnList = ((MethodNode) myMethodVisitor.getDelegate()).instructions;
        assertEquals(4, insnList.size());
        AbstractInsnNode first = insnList.getFirst();
        assertTrue(first instanceof FieldInsnNode);
        AbstractInsnNode last = insnList.getLast();
        assertTrue(last instanceof InsnNode);
        AbstractInsnNode previous = last.getPrevious();
        assertTrue(previous instanceof MethodInsnNode);
        assertEquals(191, last.getOpcode());
        AbstractInsnNode next = first.getNext();
        assertTrue(next instanceof LdcInsnNode);
        assertEquals("out", ((FieldInsnNode) first).name);
        assertEquals("java/lang/System", ((FieldInsnNode) first).owner);
        assertEquals(178, first.getOpcode());
        assertEquals("Ljava/io/PrintStream;", ((FieldInsnNode) first).desc);
        assertSame(first, next.getPrevious());
        assertEquals(18, next.getOpcode());
        assertSame(previous, next.getNext());
        assertEquals("(Ljava/lang/String;)V", ((MethodInsnNode) previous).desc);
        assertSame(next, previous.getPrevious());
        assertEquals(182, previous.getOpcode());
        assertSame(last, previous.getNext());
        assertEquals("java/io/PrintStream", ((MethodInsnNode) previous).owner);
        assertFalse(((MethodInsnNode) previous).itf);
        assertEquals("println", ((MethodInsnNode) previous).name);
        assertEquals("end", ((LdcInsnNode) next).cst);
    }

    @Test
    void testMyMethodVisitorVisitInsn3() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(new MethodNode());
        myMethodVisitor.visitInsn(177);
        InsnList insnList = ((MethodNode) myMethodVisitor.getDelegate()).instructions;
        assertEquals(4, insnList.size());
        AbstractInsnNode first = insnList.getFirst();
        assertTrue(first instanceof FieldInsnNode);
        AbstractInsnNode last = insnList.getLast();
        assertTrue(last instanceof InsnNode);
        AbstractInsnNode previous = last.getPrevious();
        assertTrue(previous instanceof MethodInsnNode);
        assertEquals(177, last.getOpcode());
        AbstractInsnNode next = first.getNext();
        assertTrue(next instanceof LdcInsnNode);
        assertEquals("out", ((FieldInsnNode) first).name);
        assertEquals("java/lang/System", ((FieldInsnNode) first).owner);
        assertEquals(178, first.getOpcode());
        assertEquals("Ljava/io/PrintStream;", ((FieldInsnNode) first).desc);
        assertSame(first, next.getPrevious());
        assertEquals(18, next.getOpcode());
        assertSame(previous, next.getNext());
        assertEquals("(Ljava/lang/String;)V", ((MethodInsnNode) previous).desc);
        assertSame(next, previous.getPrevious());
        assertEquals(182, previous.getOpcode());
        assertSame(last, previous.getNext());
        assertEquals("java/io/PrintStream", ((MethodInsnNode) previous).owner);
        assertFalse(((MethodInsnNode) previous).itf);
        assertEquals("println", ((MethodInsnNode) previous).name);
        assertEquals("end", ((LdcInsnNode) next).cst);
    }

    @Test
    void testMyMethodVisitorVisitInsn4() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new TraceMethodVisitor(new ASMifier()));
        myMethodVisitor.visitInsn(1);
        assertEquals(1, ((TraceMethodVisitor) myMethodVisitor.getDelegate()).p.getText().size());
    }

    @Test
    void testMyMethodVisitorVisitInsn5() {
        MyClassVisitor myClassVisitor = null;
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new TraceMethodVisitor(new ASMifier()));
        myMethodVisitor.visitInsn(191);
        assertEquals(4, ((TraceMethodVisitor) myMethodVisitor.getDelegate()).p.getText().size());
    }

    @Test
    void testMyMethodVisitorVisitInsn6() {
        MyClassVisitor myClassVisitor = null;
        MethodNode methodVisitor = new MethodNode();
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new TraceMethodVisitor(methodVisitor, new ASMifier()));
        myMethodVisitor.visitInsn(1);
        MethodVisitor delegate = myMethodVisitor.getDelegate();
        assertEquals(1, ((TraceMethodVisitor) delegate).p.getText().size());
        InsnList insnList = ((MethodNode) delegate.getDelegate()).instructions;
        AbstractInsnNode last = insnList.getLast();
        assertTrue(last instanceof InsnNode);
        assertEquals(1, insnList.size());
        assertSame(last, insnList.getFirst());
        assertEquals(1, last.getOpcode());
    }

    @Test
    void testMyMethodVisitorVisitInsn7() {
        MyClassVisitor myClassVisitor = null;
        MethodNode methodVisitor = new MethodNode();
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new TraceMethodVisitor(methodVisitor, new ASMifier()));
        myMethodVisitor.visitInsn(191);
        MethodVisitor delegate = myMethodVisitor.getDelegate();
        assertEquals(4, ((TraceMethodVisitor) delegate).p.getText().size());
        InsnList insnList = ((MethodNode) delegate.getDelegate()).instructions;
        assertEquals(4, insnList.size());
        AbstractInsnNode last = insnList.getLast();
        assertTrue(last instanceof InsnNode);
        AbstractInsnNode first = insnList.getFirst();
        assertTrue(first instanceof FieldInsnNode);
        assertEquals("java/lang/System", ((FieldInsnNode) first).owner);
        assertEquals("out", ((FieldInsnNode) first).name);
        assertEquals("Ljava/io/PrintStream;", ((FieldInsnNode) first).desc);
        assertEquals(178, first.getOpcode());
        AbstractInsnNode next = first.getNext();
        assertTrue(next instanceof LdcInsnNode);
        assertEquals(191, last.getOpcode());
        AbstractInsnNode previous = last.getPrevious();
        assertTrue(previous instanceof MethodInsnNode);
        assertSame(first, next.getPrevious());
        assertEquals("java/io/PrintStream", ((MethodInsnNode) previous).owner);
        assertEquals("println", ((MethodInsnNode) previous).name);
        assertFalse(((MethodInsnNode) previous).itf);
        assertEquals("(Ljava/lang/String;)V", ((MethodInsnNode) previous).desc);
        assertSame(next, previous.getPrevious());
        assertEquals(182, previous.getOpcode());
        assertSame(last, previous.getNext());
        assertEquals(18, next.getOpcode());
        assertSame(previous, next.getNext());
        assertEquals("end", ((LdcInsnNode) next).cst);
    }

    @Test
    void testMyMethodVisitorVisitInsn8() {
        MyClassVisitor myClassVisitor = null;
        TraceMethodVisitor methodVisitor = new TraceMethodVisitor(new ASMifier());
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new TraceMethodVisitor(methodVisitor, new ASMifier()));
        myMethodVisitor.visitInsn(1);
        MethodVisitor delegate = myMethodVisitor.getDelegate();
        assertEquals(1, ((TraceMethodVisitor) delegate).p.getText().size());
        assertEquals(1, ((TraceMethodVisitor) delegate.getDelegate()).p.getText().size());
    }

    @Test
    void testMyMethodVisitorVisitInsn9() {
        MyClassVisitor myClassVisitor = null;
        TraceMethodVisitor methodVisitor = new TraceMethodVisitor(new ASMifier());
        MyClassVisitor.MyMethodVisitor myMethodVisitor = myClassVisitor.new MyMethodVisitor(
                new TraceMethodVisitor(methodVisitor, new ASMifier()));
        myMethodVisitor.visitInsn(191);
        MethodVisitor delegate = myMethodVisitor.getDelegate();
        assertEquals(4, ((TraceMethodVisitor) delegate).p.getText().size());
        assertEquals(4, ((TraceMethodVisitor) delegate.getDelegate()).p.getText().size());
    }
}

