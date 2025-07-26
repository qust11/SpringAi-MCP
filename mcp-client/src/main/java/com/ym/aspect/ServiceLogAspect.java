package com.ym.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author qushutao
 * @since 2025/7/25 13:08
 **/
@Component
@Slf4j
@Aspect
public class ServiceLogAspect {

    @Around("execution(* com.ym.service.impl..*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        String pointName = joinPoint.getTarget().getClass().getName() + "." +
                joinPoint.getSignature().getName();
        long endTime = System.currentTimeMillis();
        long takeTime = endTime - startTime;
        if (takeTime > 3000){
            log.error("【{}】,【{}】,【{}】,【{}】", pointName, takeTime, "SLOW", joinPoint.getArgs());
        }else {
            log.info("【{}】,【{}】,【{}】,【{}】", pointName, takeTime, "OK", joinPoint.getArgs());
        }
        return proceed;
    }
}
