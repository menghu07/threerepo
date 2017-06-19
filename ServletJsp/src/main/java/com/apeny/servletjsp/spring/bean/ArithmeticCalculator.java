package com.apeny.servletjsp.spring.bean;

/**
 * 接口aop切入
 * @author mengqh
 *
 */
public interface ArithmeticCalculator {
    
    void add(int i, int j);
    
    void sub(int i, int j);
    
    void mul(int i, int j);
    
    void div(int i, int j);
}
