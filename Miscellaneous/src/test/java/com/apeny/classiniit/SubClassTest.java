package com.apeny.classiniit;

import org.aopalliance.aop.Advice;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

/**
 * Created by monis on 2018/9/9.
 */
public class SubClassTest implements Advice {
    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        SuperClass 中为3 = null;
        new SubClass();
    }

    private void testTwo2() {
        System.out.println("hai lowed");
    }

    private void testThree() {
        FunInterfaceOne funInterfaceOne = this::testTwo2;
    }

    class Mile implements FactoryBean<Advice> {

        @Nullable
        @Override
        public SubClassTest getObject() throws Exception {
            return null;
        }

        @Nullable
        @Override
        public Class<?> getObjectType() {
            return null;
        }
    }
}


interface FunInterfaceOne {
    void testTwo();
}