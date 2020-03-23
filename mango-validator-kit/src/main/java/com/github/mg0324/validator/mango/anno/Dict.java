package com.github.mg0324.validator.mango.anno;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Dict.Validator.class)
public @interface Dict {

    String message() default "错误字典类型值";
    String allowValue() default "";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};

    class Validator implements ConstraintValidator<Dict, String> {
        List<String> allowList;

        public void initialize(Dict dict) {
            allowList = new ArrayList<String>();
            String allowValue = dict.allowValue();
            for(String key : allowValue.split(",")){
                allowList.add(key);
            }
        }


        public boolean isValid(String str, ConstraintValidatorContext cvc) {
            if(str == null) return true;
            if(str.trim().equals("")) return true;
            if(allowList.contains(str)){
                return true;
            }else{
                //System.out.println("error:"+str+" in "+allowList);
                return false;
            }
        }
    }
}