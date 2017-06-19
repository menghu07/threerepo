package com.apeny.servletjsp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class ValidationAspect {
	
	@Before("execution(* *.*(int, int))")
	public void beforeMethod(JoinPoint point) {
		System.out.println("advice before order 0 is called. " + point);
	}
}
