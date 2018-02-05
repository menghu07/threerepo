package com.apeny.environment;

/**
 * Created by apeny on 2018/1/27.
 */
public class CanonicalClass {
    public static void main(String[] args) {
        innerClass();
    }

    /**
     * 局部内部类，匿名内部类，及其对应的数组没有Class.getCanonicalName()
     */
    private static void innerClass() {
        class CannonicalInnerClass {

        }
        CannonicalInnerClass innerClass = new CannonicalInnerClass();
        String canonicalName = innerClass.getClass().getCanonicalName();
        System.out.println("CannonicalInnerClass canonicalName: " + canonicalName);
        int[] arrInt = new int[10];
        System.out.println("array int canonicalName: " + arrInt.getClass().getCanonicalName());
        CannonicalInnerClass[] canonicalInnerClasses = new CannonicalInnerClass[10];
        System.out.println("CanonicalInnerClasses canonicalName: " + canonicalInnerClasses.getClass().getCanonicalName());
        System.out.println("array ComponentType" + canonicalInnerClasses.getClass().getComponentType());
        System.out.println("canonical normal class: " + CanonicalNormalInnerClass.class.getCanonicalName() + "; CanonicalClass$1CanonicalInnerClass: "
                + CannonicalInnerClass.class.getName());
    }

    class CanonicalNormalInnerClass {

    }
}
