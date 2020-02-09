package com.yyjj.controller.dynamic.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.yyjj.controller.dynamic.ActivityEum;

@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD })
public @interface ApiLog {

	String type();//api类型  如资源 、业务阶段、基金等值 
	
	ActivityEum activity();//活动类型 动作 如添加了、删除了、修改了
	
	String message() default "";//消息 //附加消息
	
	/**
	 *  1:动态  
	 *  2：重要 
	 *  20：项目 
	 *  21：项目&动态 
	 *  22：项目&重要
	 *  23: 项目不相干
	 *  30:投后
	 *  31：投后&动态
	 *  32：投后&重要
	 *  33：投后不相干
	 * @return
	 */
	int messageType() default 1;
		
	
	int proOrAfter() default 0;//1：项目动态 2：投后动态
	//Class<?> 
}
