package com.mango.validator.anno;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *多选字典，值用逗号分隔
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DictMulti.Validator.class)
public @interface DictMulti {

    String message() default "错误字典类型值";
    String allowValue() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

    class Validator implements ConstraintValidator<DictMulti, String> {
        List<String> allowList;

        public void initialize(DictMulti dict) {
            allowList = new ArrayList<String>();
            String allowValue = dict.allowValue();
            for(String key : allowValue.split(",")){
                allowList.add(key);
            }
        }


        public boolean isValid(String str, ConstraintValidatorContext cvc) {
            if(str == null) return true;
            if(str.trim().equals("")) return true;
            String[] arr = str.split(",");
            List<String> valueList = Arrays.asList(arr);
            if(allowList.containsAll(valueList)){
                return true;
            }else{
                return false;
            }
        }
    }
}