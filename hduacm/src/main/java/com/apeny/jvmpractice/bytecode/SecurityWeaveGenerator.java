package com.apeny.jvmpractice.bytecode;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by apeny on 2017/11/5.
 */
public class SecurityWeaveGenerator {
    public static void main(String[] args) throws Exception {
        testWeave();
    }

    public static void visitAccount() {
        Account account = new Account();
        account.operation(7);
    }

    public static void testWeave() throws Exception {
        String className = Account.class.getName();
        ClassReader cr = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        SecurityCheckClassApapter classApapter = new SecurityCheckClassApapter(classWriter);
        cr.accept(classApapter, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
        File file = new File("target/classes/" + className.replace('.', '/') + ".class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
}

class Account {
    public void operation(int i) {
        if (SecurityChecker.checkSecurity()) {
            System.out.println("zziiifffaaa");
            return;
        } else {
            System.out.println("operation....");
        }
    }
}

class SecurityChecker {
    public static boolean checkSecurity() {
        System.out.println("securityChecker.checkSecurity...");
        if ((System.nanoTime() & 1) == 0) {
            return false;
        } else {
            return true;
        }
    }
}

class SecurityCheckClassApapter extends ClassVisitor {

    public SecurityCheckClassApapter(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exception) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exception);
        MethodVisitor wrappedMv = mv;
        if (mv != null) {
            if (name.equals("operation")) {
                wrappedMv = new SecurityCheckMethodAdapter(mv);
            }
        }
        return wrappedMv;
    }
}

class SecurityCheckMethodAdapter extends MethodVisitor {
    public SecurityCheckMethodAdapter(MethodVisitor methodVisitor) {
        super(Opcodes.ASM5, methodVisitor);
    }

    public void visitCode() {
        Label continueLabel = new Label();
        visitMethodInsn(Opcodes.INVOKESTATIC, "com/apeny/jvmpractice/bytecode/SecurityChecker", "checkSecurity", "()Z");
        visitJumpInsn(Opcodes.IFNE, continueLabel);
        visitInsn(Opcodes.RETURN);
        visitLabel(continueLabel);
        super.visitCode();
    }
}