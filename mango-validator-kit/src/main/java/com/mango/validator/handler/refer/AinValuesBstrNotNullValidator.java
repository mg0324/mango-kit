package com.mango.validator.handler.refer;



import com.mango.validator.handler.ReferValidator;
import com.mango.validator.util.ValidContext;

import java.util.Map;

/**
 * Created by meigang on 17/9/15.
 */
public class AinValuesBstrNotNullValidator extends ReferValidator {
    /**
     * @param param 条件参数
     * @param validparam 验证参数
     * @param vc 验证上下文
     * @param msg 错误信息
     * @return
     */
    @Override
    public boolean validate(Map<String, Object> param, Map<String, Object> validparam, ValidContext vc, String msg) {
        String bb = this.getString(param,this.getFirstKey(param));
        String eqValue = this.getString(param,this.getFirstKey(param)+"_eqValue");
        if(null != bb){
            String field = this.getFirstKey(validparam);
            String str  = this.getString(validparam, field);
            if(isInStr(eqValue,bb)) {
                if (null != str && str.length()>0) {
                    return true;
                } else {
                    vc.addError(field, msg);
                    return false;
                }
            }else{
                return true;
            }
        }
        return true;
    }

    private boolean isInStr(String eqValue,String value){
        boolean b = false;
        String[] arr = eqValue.split(",");
        for(String str : arr){
            if(value.equals(str)){
                b = true;
                break;
            }
        }
        return b;
    }
}
