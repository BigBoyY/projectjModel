package com.yyjj.controller.dynamic.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 标注该注解声明日志加入项目动态 只能注解在projectId上
 * @author admin
 *
 */
@Retention(RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Pro {

}
