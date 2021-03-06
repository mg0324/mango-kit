package com.github.mg0324.validator.mango.handler.refer;



import com.github.mg0324.validator.mango.handler.ReferValidator;
import com.github.mg0324.validator.util.ValidContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by meigang on 17/9/15.
 */
public class AcontainsValuesBstrNotNullValidator extends ReferValidator {
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
        String[] eqArr = eqValue.split(",");//eqValue中的每一个值，都要在value中存在，返回true
        String[] valueArr = value.split(",");
        List<String> eqList = Arrays.asList(eqArr);
        List<String> valueList = Arrays.asList(valueArr);
        if(valueList.containsAll(eqList)){
            b = true;
        }
        return b;
    }
}
