package com.apeny.jvmpractice.bytecodeexec;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 动态函数调用
 * Created by apeny on 2017/11/1.
 */
public class SimpleMethodHandle {
    public static void main(String[] args) throws Throwable {
        Object obj = ((System.currentTimeMillis()) & 1L) == 0 ? System.out : new MyPrinln();
        MethodHandle methodHandle = getHandler(obj);
        methodHandle.invokeExact("you are right?");
    }

    private static MethodHandle getHandler(Object receiver) throws Throwable {
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
    }

    static class MyPrinln {
        public void println(String str) {
            System.out.println("my println: " + str);
        }
    }

}
