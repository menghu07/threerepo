package com.apeny.servletjsp.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.apeny.servletjsp.spring.bean.ArithmeticCalculator;
import com.apeny.servletjsp.spring.bean.ArithmeticCalculatorImpl;

public class TestLogAspect {
    
    public static void main(String[] args) {
        
        System.out.println("args " + args);
        TestLogAspect testAspect = new TestLogAspect();
        testAspect.callLog();
    }
    
    public void callLog() {
        //config/applicationContext.xml
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/config/applicationContext.xml");
        ArithmeticCalculator calculator = context.getBean(ArithmeticCalculator.class);
//        ArithmeticCalculatorImpl  impl = (ArithmeticCalculatorImpl) context.getBean("arithmeticCalculatorImpl");
//        ArithmeticCalculatorImpl  impl2 = (ArithmeticCalculatorImpl) context.getBean(ArithmeticCalculatorImpl.class);
        calculator.add(2, 3);
//        impl.add(2, 90);
//        impl.toString();
         context.close();
        
//        context.close();
    }
}
