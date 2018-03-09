package com.echounion.boss.core.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于描述类和方法的作用
 * @author 胡礼波
 * 2012-11-1 上午11:09:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE,ElementType.METHOD})
public @interface ActionModel {
	String description() default "";
}
