package com.yyjj.controller.dynamic.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyjj.context.SpringApplicationUtils;
import com.yyjj.controller.dynamic.ApiLogContextHolder;
import com.yyjj.controller.dynamic.annotation.ApiId;
import com.yyjj.model.dynamic.Inform;


public class ApiIdAspect {
	// 定义切点 --> 拦截ApiDelete
	@Pointcut("@annotation(com.yyjj.controller.dynamic.annotation.ApiId)")
	public void apiIdLog() {

	}

	/**
	 * 执行前获取控制器参数信息
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Before("apiIdLog()")
	public void doBefore(JoinPoint pjp){
		Inform inform = null;
		// 获取控制器
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取具体的方法
		Method method = methodSignature.getMethod();
		// 获取注解
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		inform = ApiLogContextHolder.getInform();
		inform.setMethod(HttpMethod.DELETE.toString());
		//设置详情
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation annotation : parameterAnnotations[i]) {
				if (annotation instanceof ApiId) {
					ApiId apiDelete = (ApiId) annotation;
					Object detail = doGetDetail(pjp.getArgs()[i],apiDelete);
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
	 * @param delete 注解
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object doGetDetail(Object obj,ApiId delete) {
		@SuppressWarnings("rawtypes")
		IService iService = SpringApplicationUtils.getBean(delete.value());		
		if (Objects.nonNull(obj)) {
			if (obj instanceof Integer) {
				Object detail = iService.getById((int) obj);
				return getDetail(detail,delete);
			} else if (obj instanceof Integer[]) {
				Integer[] ids = (Integer[]) obj;				
				Collection<?> objList = iService.listByIds(Arrays.asList(ids));
				StringBuilder detailString = new StringBuilder();
				for (Object object : objList) {
					detailString.append(getDetail(object,delete));
				}
				return detailString.toString();				
			}
		}
		return "";
	}

	private Object getDetail(Object entity, ApiId delete) {
		java.lang.reflect.Field field;
		try {
			field = entity.getClass().getDeclaredField(delete.detailParam());
			field.setAccessible(true);
			Object detail;
				detail = field.get(entity);			
			return  detail;
		} catch (Exception e) {
			return "";
		} 
		
	}
}
