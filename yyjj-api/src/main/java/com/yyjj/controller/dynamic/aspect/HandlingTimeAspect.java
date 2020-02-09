package com.yyjj.controller.dynamic.aspect;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 打印接口处理时间
 * 
 * @author yml
 *
 */
@Aspect
@Component
public class HandlingTimeAspect {
	@Pointcut("execution(* com.yyjj.controller..*.*(..))")
	public void  handlingTime() {
	}
	private Long beginTime ;
	
	private Long endTime;
	private static final Logger log = LoggerFactory.getLogger(HandlingTimeAspect.class);
	
	
	@Before("handlingTime()")
	public void doBefore(JoinPoint pjp) throws Throwable {
		//获取毫秒数
		setBeginTime( LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
	}
	
	@AfterReturning(value = "handlingTime()", returning = "rvt")
	public void doAfterReturning(JoinPoint pjp, Object rvt) throws NoSuchFieldException, SecurityException {
		setEndTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
		if(endTime-beginTime>500) {
			log.warn("警告：接口处理时间过长："+(endTime-beginTime)+"毫秒");
		}else {
			log.info("接口处理时间："+(endTime-beginTime)+"毫秒");
		}
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	
}
