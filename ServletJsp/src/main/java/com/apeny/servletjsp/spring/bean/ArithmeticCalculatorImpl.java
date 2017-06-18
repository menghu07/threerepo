package com.apeny.servletjsp.spring.bean;

import org.springframework.stereotype.Component;

@Component
public class ArithmeticCalculatorImpl implements ArithmeticCalculator  {

    public void add(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i + j) );
    }

    public void sub(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i - j) );
    }

    public void mul(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i * j) );
    }

    public void div(int i, int j) {
        System.out.println("i = " + i + ", j = " + j + " === " + (i / j) );
    }
    
    public String toString() {
        System.out.println("to string is called");
        return "string to";
    }
}
