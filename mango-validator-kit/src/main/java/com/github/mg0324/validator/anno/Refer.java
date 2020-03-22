package com.github.mg0324.validator.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by meigang on 17/9/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Refer {
    String[] param();//条件验证参数
    String[] validparam() default {};//要验证的值验证参数，未配置则默认是注解上的属性值
    Class<?> validator();//验证器
    String message() default "";
    Class<?>[] groups() default {};//分组

}
