package org.bmartins.sandbox.springaspectj.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimingAspect {
		
	private static Logger LOG = LoggerFactory.getLogger(TimingAspect.class);
	
	@Pointcut("execution(* org.bmartins.sandbox.springaspectj..*.*(..))")	
	public void anyExecution() {}

	@Around("anyExecution()")
	public Object aroundAnyExecution(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		
		Object result = pjp.proceed();
		
		long elapsed = System.currentTimeMillis() - start;
		LOG.info("{}: elapsed: {}ms", pjp.getSignature(), elapsed);		
		
		return result;
	}
}
