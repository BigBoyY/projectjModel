package com.yyjj.handle;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yyjj.context.RequestHolder;

@Aspect
@Component
public class requestAspect {
		@Pointcut("execution(* com.oudot.asset.manage.api.controller..*(..))")
	    public void request() {
	    }
	
	 	@Before("request()")
	    public void doBefer(JoinPoint pjp) throws Throwable {
	        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        if (Objects.nonNull(sra)) {
	            HttpServletRequest request = sra.getRequest();
	            RequestHolder.setRequest(request);	     
	        }
	 	}
}
