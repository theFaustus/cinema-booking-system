package com.evil.cbs.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
@Slf4j
public class LoggingAspect {
    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.evil.cbs.service..*)" +
            " || within(com.evil.cbs.web..*)" +
            " || within(com.evil.cbs.config..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "() with cause = \'" + ((e.getCause() != null) ? e.getCause() : "NULL") + "\' and exception = \'" + e.getMessage() + "\'", e);
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "() with argument[s] = " + Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "() with result = " + result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: " + Arrays.toString(joinPoint.getArgs()) + " in " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()");

            throw e;
        }
    }
}

