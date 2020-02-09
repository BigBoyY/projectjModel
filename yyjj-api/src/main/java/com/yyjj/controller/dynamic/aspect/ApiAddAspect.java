package com.yyjj.controller.dynamic.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.yyjj.controller.dynamic.ApiLogContextHolder;
import com.yyjj.controller.dynamic.annotation.ApiAdd;
import com.yyjj.model.dynamic.Inform;

@Aspect
@Component
public class ApiAddAspect {
	// 定义切点 --> 拦截ApiAdd
	
	  @Pointcut("@annotation(com.yyjj.controller.dynamic.annotation.ApiAdd)")
	  public void apiAddLog() {
	  
	  }
	 ///yyjj-api/src/main/java/com/yyjj/controller/dynamic/annotation/ApiAdd.java
	private static final Logger log = LoggerFactory.getLogger(ApiAddAspect.class);
	/**
	 * 执行前获取控制器参数信息
	 * /asset-manage-api/src/main/java/com/oudot/asset/manage/api/VO/project/ProjectAddVO.java
	 * @param pjp
	 * @return 
	 * @throws Throwable
	 */
	@Before("apiAddLog()")
	public void doBefore(JoinPoint pjp) throws Throwable {
		Inform inform = null;
		// 获取控制器
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取具体的方法
		Method method = methodSignature.getMethod();
		// 获取注解
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		inform = ApiLogContextHolder.getInform();
	
		inform.setMethod(HttpMethod.POST.toString());
		//设置详情
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation annotation : parameterAnnotations[i]) {
				if (annotation instanceof ApiAdd) {
					ApiAdd apiAdd = (ApiAdd) annotation;
					Object detail = doGetDetail(pjp.getArgs()[i],apiAdd);
					if (detail instanceof String) {
						inform.setDetail((String)detail);
						ApiLogContextHolder.setInform(inform);
					}
				}
			}
		}
	}
	
	/**
	 * 解析注解中的service 执行ById查找到bean 并获取详细信息
	 * @param obj 参数
	 * @param add 注解
	 * @return
	 */
	private Object doGetDetail(Object obj,ApiAdd add) {
		Field field;
		try {
			field = obj.getClass().getDeclaredField(add.value());
			if (Objects.nonNull(field)) {
				field.setAccessible(true);
				return field.get(obj);
			}
		} catch (Exception e) {
			return "";
		} 
		return "";	
	}
}
