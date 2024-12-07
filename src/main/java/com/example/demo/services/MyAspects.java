package com.example.demo.services;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspects {
    @Before("execution(* com.example.demo.services.HouseholdService.*(..))")
    public void logBeforeServiceExecution(JoinPoint joinPoint) {
        log.info("executing {} with arguments: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @After("execution(* com.example.demo.services.HouseholdService.*(..))")
    public void logAfterServiceExecution(JoinPoint joinPoint) {
        log.info("Finished executing {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "execution(* com.example.demo.services.HouseholdService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method {} returned with value: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(value = "execution(* com.example.demo.services.HouseholdService.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in {} with cause: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }

    @Around("execution(* com.example.demo.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), executionTime);
        return result;
    }
}
