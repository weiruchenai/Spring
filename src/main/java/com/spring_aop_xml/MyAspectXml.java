package com.spring_aop_xml;

import org.aspectj.lang.JoinPoint;

public class MyAspectXml {
    public void checkPri(JoinPoint joinPoint){
        System.out.println("校验成功..." + joinPoint);
    }

    public void logDocumented(){
        System.out.println("日志记录成功...");
    }

    public void searchSuccess(Object result){
        System.out.println("成功查询..." + result);
    }

    public void exceptionThrow(Throwable ex){
        System.out.println("异常抛出了..." + ex.getMessage());
    }
}
