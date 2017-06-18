package com.apeny.servletjsp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component("LogAspect")
public class LogAspect {
    
    @Pointcut(value = "execution(* *.*())")
    public void pointCut() {
        
    }
    
    @Before("execution(* *..*.*(int, int))")
    public void beforeLogger(JoinPoint joinPoint) {
        System.out.println("join point like this: " + joinPoint);
    }
}
