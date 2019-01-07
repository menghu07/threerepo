package com.apeny.classiniit;

import org.junit.Test;
import org.springframework.util.ClassUtils;

/**
 * Created by monis on 2019/1/6.
 */
public class ClassUtilsTest {

    @Test
    public void testClasses() {
        String[] arrString = new String[1];
        System.out.println("string[]: predict【[Ljava.lang.String;】 " + arrString.getClass().getName());
        try {
            Class stringClass = ClassUtils.forName("java.lang.String[]", ClassUtilsTest.class.getClassLoader());
            System.out.println("类声明 stringClass: " + stringClass);

            Class intArrClass = ClassUtils.forName("[[I", ClassUtilsTest.class.getClassLoader());
            System.out.println("intArrClass: " + intArrClass + ", " + new int[1][1].getClass());

            Class integerArrClass = ClassUtils.forName("[[Ljava.lang.Integer", ClassUtilsTest.class.getClassLoader());
            System.out.println("integerArrClass: " + intArrClass + ", " + new Integer[1][1].getClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
