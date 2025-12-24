package com.akshay.project.project.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

	@After("execution(* com.akshay.project.project.service.*.*(..)")
	public void afterExecution(JoinPoint joinPoint) {
		log.info(" class: {}, method: {}, message: {}", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName());
	}

	@AfterThrowing(pointcut = "execution(* com.akshay.project.project.service.*.*(..)", throwing = "ex")
	public void exceptionAspect(JoinPoint joinPoint, Exception ex) {
		log.error("Exception in class: {}, method: {}, message: {}", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName(), ex.getMessage(), ex);
	}
}
