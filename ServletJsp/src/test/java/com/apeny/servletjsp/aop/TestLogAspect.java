package com.apeny.servletjsp.aop;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.apeny.servletjsp.spring.bean.ArithmeticCalculator;

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
        ArithmeticCalculator  impl = (ArithmeticCalculator) context.getBean("arithmeticCalculatorImpl");
        calculator.add(2, 3);
        impl.add(9, 293);
        context.close();
    }
}
