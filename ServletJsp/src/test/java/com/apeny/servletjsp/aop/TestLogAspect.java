package com.apeny.servletjsp.aop;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.apeny.servletjsp.spring.bean.ArithmeticCalculator;

public class TestLogAspect {

    public static void main(String[] args) {

        System.out.println("args " + args);
        TestLogAspect testAspect = new TestLogAspect();
        testAspect.callLog();
//        testAspect.testFormat();
        testAspect.testFormatDate();
    }

    public void callLog() {
        // config/applicationContext.xml
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "src/main/resources/config/applicationContext.xml");
        ArithmeticCalculator calculator = context.getBean(ArithmeticCalculator.class);
        ArithmeticCalculator impl = (ArithmeticCalculator) context.getBean("arithmeticCalculatorImpl");
        calculator.add(2, 3);
        impl.add(9, 293);
        context.close();
    }
    
    public void testFormatDate() {
    	System.out.println(new Date());
    }
    public void testFormat() {

//        double pi = 3.1415927;
        double pi = 1298989901.89902;
        System.out.println("(-0.0 < 0.0)" + (-0.0 > 0.0));
        // 取一位整数
//        System.out.println(newDecimalFormat("0").format(pi));
//        // 取一位整数和两位小数
//        System.out.println(newDecimalFormat("0.00").format(pi)); // 3.14
//        // 取两位整数和三位小数，整数不足部分以0填补。
//        System.out.println(new DecimalFormat("00.000").format(pi));// 03.142
//        // 取所有整数部分
//        System.out.println(newDecimalFormat("#").format(pi)); // 3
//        // 以百分比方式计数，并取两位小数
//        System.out.println(new DecimalFormat("#.##%").format(pi)); // 314.16%
        //with #
        System.out.println(new DecimalFormat("#,##00.##\u2030").format(pi)); // 314.16%

        long c = 299792458; // 光速
        // 显示为科学计数法，并取五位小数
        System.out.println(newDecimalFormat("#.#####E0").format(c));// 2.99792E8
        // 显示为两位整数的科学计数法，并取四位小数
        System.out.println(newDecimalFormat("00.####E0").format(c));// 29.9792E7
        // 每三位以逗号进行分隔。
        System.out.println(newDecimalFormat(",###").format(c));// 299,792,458
        // 将格式嵌入文本
        System.out.println(newDecimalFormat("光速大小为每秒,###米。").format(c));

    }

    private DecimalFormat newDecimalFormat(String format) {
        return new DecimalFormat(format);
    }
}
