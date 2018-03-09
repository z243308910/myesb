package com.echounion.bossmanager.common.security.annotation;
import java.lang.annotation.*;

import com.echounion.bossmanager.common.enums.LoginFlag;


/**
 * 权限控制注解
 * @author 胡礼波
 * 2012-4-25 下午05:36:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionRightCtl
{
	LoginFlag login();
}
