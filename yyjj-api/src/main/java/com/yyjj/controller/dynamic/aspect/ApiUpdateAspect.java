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
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.yyjj.controller.dynamic.ApiLogContextHolder;
import com.yyjj.controller.dynamic.annotation.ApiUpdate;
import com.yyjj.model.dynamic.Inform;

@Aspect
@Component
public class ApiUpdateAspect {
	// 定义切点 --> 拦截ApiUpdate
	@Pointcut("@annotation(com.yyjj.controller.dynamic.annotation.ApiUpdate)")
	public void apiUpdateLog() {

	}

	/**
	 * 执行前获取控制器参数信息
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Before("apiUpdateLog()")
	public void doBefore(JoinPoint pjp) throws Throwable {
		Inform inform = null;
		// 获取控制器
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取具体的方法
		Method method = methodSignature.getMethod();
		// 获取注解
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		inform = ApiLogContextHolder.getInform();
		inform.setMethod(HttpMethod.PUT.toString());
		//设置详情
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation annotation : parameterAnnotations[i]) {
				if (annotation instanceof ApiUpdate) {
					ApiUpdate apiUpdate = (ApiUpdate) annotation;
					Object detail = doGetDetail(pjp.getArgs()[i],apiUpdate);
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
	 * @param update 注解
	 * @return
	 */
	private Object doGetDetail(Object obj,ApiUpdate update) {
		Field field;
		try {
			field = obj.getClass().getDeclaredField(update.value());
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
