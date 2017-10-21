package com.mango.validator.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by meigang on 17/10/21
 * bean中的属性值，在数据库中唯一，区分编辑和新增
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unique {
    String mapperMethod();//mybatis mapper method
    String[] sqlparam() default {};//sql的过滤条件
    String message() default "";
    Class<?>[] groups() default {};//分组

}
