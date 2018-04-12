package com.digital.util.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
/* 定义字段的注解*/
@Retention(RetentionPolicy.RUNTIME)
/*该注解只能用在成员变量上*/
@Target(ElementType.FIELD)
public @interface Column {

    String name() default "";
}
