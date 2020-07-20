package com.spring_aop_annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyAspectAnnotation {
    @Before(value = "execution(* com.spring_aop_annotation.OrderDaoImpl.add(..))")
    public void before(){
        System.out.println("before...");
    }

    @Around(value = "execution(* com.spring_aop_annotation.OrderDaoImpl.update())")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("before around...");
        Object object = joinPoint.proceed();
        System.out.println("after around...");
        return object;
    }
}
