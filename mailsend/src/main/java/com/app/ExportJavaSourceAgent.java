package com.app;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * 导出内存中class文件
 *
 * @author apeny
 */
public class ExportJavaSourceAgent implements ClassFileTransformer {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ExportJavaSourceAgent());
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className == null || "".equals(className)) {
            return classfileBuffer;
        }
        if (!className.startsWith("java") && !className.startsWith("sun")) {
            // 既不是java也不是sun开头的
            // 导出代码
            if (className.startsWith("/")) {
                className = className.substring(1);
            }
            int lastIndexOf = className.lastIndexOf("/") + 1;
            String subPath = "";
            if (lastIndexOf > 0) {
                subPath = className.substring(0, lastIndexOf);
            }
            String fileName = className.substring(lastIndexOf) + ".class";
            exportClazzToFile("F:/exportjavasource/src/" + subPath, fileName, classfileBuffer);
            System.out.println(className + " --> export java succeess!");
        }
        return classfileBuffer;
    }

    /**
     * @param dirPath  目录以/结尾，且必须存在
     * @param fileName
     * @param data
     */
    private void exportClazzToFile(String dirPath, String fileName, byte[] data) {
        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File classFile = new File(dirPath + fileName);
            FileOutputStream fos = new FileOutputStream(classFile);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            System.out.println("exception occured while doing some file operation");
            e.printStackTrace();
        }
    }
}