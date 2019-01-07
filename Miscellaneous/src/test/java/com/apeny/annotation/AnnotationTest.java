package com.apeny.annotation;

import java.lang.annotation.*;

/**
 * Created by monis on 2018/9/8.
 */

public class AnnotationTest {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        System.out.println(AnOne.class.getAnnotations()[0] == AnOne.class.getAnnotations()[0]);
        System.out.println(AnOne.class.getAnnotation(One.class).hashCode());
        System.out.println(AnOne.class.getAnnotation(One.class) == AnTwo.class.getAnnotations()[0]);
        System.out.println(AnTwo.class.getAnnotation(One.class));
        System.out.println(AnTwo.class.getAnnotation(One.class).hashCode());

    }
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface One {
    int value() default 10;
}

@One(100)
class AnOne {

}

@One(200)
class AnTwo {

}