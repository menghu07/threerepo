package com.apeny.servletjsp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * advice before order 0 is called. execution(int com.apeny.servletjsp.spring.bean.ArithmeticCalculator.add(int,int))
[before around] join point like this: execution(int com.apeny.servletjsp.spring.bean.ArithmeticCalculator.add(int,int))
[before] join point like this: execution(int com.apeny.servletjsp.spring.bean.ArithmeticCalculator.add(int,int))
i = 9, j = 293 === 302
[after around] join point like this: execution(int com.apeny.servletjsp.spring.bean.ArithmeticCalculator.add(int,int)) return value = 302
[after] join point like this: execution(int com.apeny.servletjsp.spring.bean.ArithmeticCalculator.add(int,int))
[after returning] join point like this: execution(int com.apeny.servletjsp.spring.bean.ArithmeticCalculator.add(int,int))
 */
@Aspect
@Component("LogAspect")
@Order(1)
public class LogAspect {
    
    @Pointcut(value = "execution(* *.*())")
    public void pointCut() {
        
    }
    
    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("execution(* *..*.*(int, int))")
    public void beforeLogger(JoinPoint joinPoint) {
        System.out.println("[before] join point like this: " + joinPoint);
    }
    
    /**
     * 后置通知
     * @param joinPoint
     */
    @After("execution(* *.*(int, int))")
    public void afterLogger(JoinPoint joinPoint) {
    	System.out.println("[after] join point like this: " + joinPoint);    	
    }
    
    /**
     * 返回通知
     * 通知中参数必须在通知注解中绑定
     * 如果切点方法有返回值，那么通知也要有返回值
     * @param joinPoint
     */
    @AfterReturning(value = "execution(* *.*(int, int))", returning = "retVal")
    public Object afterReturing(JoinPoint joinPoint, Object retVal) {    	
    	System.out.println("[after returning] join point like this: " + joinPoint);    	
    	return retVal;
    }
    
    /**
     * 异常通知
     * @param joinPoint
     */
    @AfterThrowing(value = "execution(* *.*(int, int))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
    	System.out.println("[after throwing] join point like this: " + joinPoint);    	
    }
    
    /**
     * 相当于 {@code @Before @AfterReturning @AfterThrowing}组合
     * @param joinPoint
     */
    @Around(value = "execution(* *.*(int, int))")
    public Object around(ProceedingJoinPoint joinPoint) {
    	Object result = null;
    	System.out.println("[before around] join point like this: " + joinPoint);
    	try {
    		result = joinPoint.proceed();
    	} catch (Throwable ex) {
    		ex.printStackTrace();
    	}
    	System.out.println("[after around] join point like this: " + joinPoint + " return value = " + result);
    	return result;
    }
    
}
