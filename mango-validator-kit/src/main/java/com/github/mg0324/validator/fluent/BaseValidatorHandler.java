package com.github.mg0324.validator.fluent;

import com.alibaba.fastjson.JSONObject;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * fluent base 验证处理器
 */
public class BaseValidatorHandler<T> extends ValidatorHandler<T> {

    public boolean handleJsonPattern(ValidatorContext context, T t,String errorMsg){
        if(null == t) return true;
        boolean b = false;
        try {
            Object obj = JSONObject.parse(t.toString());
            if (obj instanceof JSONObject) {
                b = true;
            } else {
                try {
                    JSONObject json = (JSONObject)JSONObject.toJSON(obj);
                    b = true;
                } catch (RuntimeException var3) {
                    b =false;
                }
            }
        }catch (Exception e){
            b = false;
        }
        if(b){
            return true;
        }else{
            // 字符串不是合法json格式
            context.addError(
                    ValidationError.create(errorMsg)
            );
            return false;
        }
    }

    protected boolean handleClassPattern(ValidatorContext context, String t, String errorMsg) {
        boolean b = false;
        try {
            Class.forName(t,false,this.getClass().getClassLoader());
            b = true;
        } catch (ClassNotFoundException e) {
            b = false;
        }
        if(b){
            return true;
        }else{
            // 字符串不是合法json格式
            context.addError(
                    ValidationError.create(errorMsg)
            );
            return false;
        }
    }
}
