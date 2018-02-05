package com.apeny.varargs;

/**
 * Created by apeny on 2018/2/3.
 */
public class VarClass {
    public VarClass() {

    }

    public VarClass(Class arg) {

    }

    public VarClass(Class... args) {

    }

    public static void main(String[] args) {
        testClasses();
    }

    /**
     * 优先匹配一个参数
     *
     * @param args
     */
    private static void varClass(Class... args) {
        if (args == null) {
            System.out.println("var Class is null");
        } else {
            System.out.println(args.length);
        }
    }

    private static void varClass() {
        System.out.println("var no args");
    }

//    private static void varClass(Class arg) {
//        System.out.println("has one parameter: " + arg);
//    }

    private static void testClasses() {
        //~~varClass(null) 在没有varClass()时
        varClass( null);
        varClass((Class[]) null);

        varClass(new Class[0]);
        varClass(new Class[] {null});
        varClass((Class) null);
        varClass(null, Class.class);
        varClass();
        varClass(new Class[8]);
        try {
            //~~
            System.out.println("class VarClass Constructor no args: " + VarClass.class.getDeclaredConstructor());
            System.out.println("class VarClass Constructor no args but null is its args converted Class[]: " + VarClass.class.getDeclaredConstructor((Class[]) null));
            System.out.println("class VarClass Constructor no args but null is its args: " + VarClass.class.getDeclaredConstructor(null));
            //~~
            System.out.println("class VarClass Constructor no args but null is its args converted Class[]: " + VarClass.class.getDeclaredConstructor(new Class[0]));
            //~~==
            //! System.out.println("class VarClass Constructor no args but null is its args converted Class[]: " + VarClass.class.getDeclaredConstructor((Class) null));
            //! System.out.printf("%s;;;;", null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
