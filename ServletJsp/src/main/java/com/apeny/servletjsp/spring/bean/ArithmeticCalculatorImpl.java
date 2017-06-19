package com.apeny.servletjsp.spring.bean;

import org.springframework.stereotype.Component;

@Component
public class ArithmeticCalculatorImpl implements ArithmeticCalculator  {

    public int add(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i + j) );
        if (i < 10) {
        	throw new IllegalArgumentException();
        }
        return i + j;
    }

    public int sub(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i - j) );
        return i - j;
    }

    public int mul(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i * j) );
        return i * j;
    }

    public int div(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i / j) );
        return i / j;
    }
    
    public String toString() {
        System.out.println("to string is called");
        return "string to";
    }
}
