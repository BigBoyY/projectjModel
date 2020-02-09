package com.yyjj.controller.dynamic.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.baomidou.mybatisplus.extension.service.IService;

@Retention(RUNTIME)
@Target({ElementType.PARAMETER})
public @interface  ApiId  {
	Class<? extends IService<?>> value();
	
	String detailParam();
	
	String idName() default "";
}
