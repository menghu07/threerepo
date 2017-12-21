package com.apeny.trycatch;

/**
 * Created by apeny on 2017/12/21.
 */
public class ThrowErrorFinally {

    public static void main(String[] args) {
        execute();
    }

    /**
     * throw error 可以执行finally
     */
    public static void execute() {
        try {
            System.out.println("throw new error");
            throw new Error();
        } catch (Exception e) {
            System.out.println("e" + e);
        } finally {
            System.out.println("i want to see can execute?");
        }
    }
}
