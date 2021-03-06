package com.github.mg0324.validator.mango.handler.refer;


import com.github.mg0324.validator.mango.handler.ReferValidator;
import com.github.mg0324.validator.util.ValidContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by meigang on 17/9/15.
 */
public class AfilterBlistNotNullValidator extends ReferValidator {

    @Override
    public boolean validate(Map<String, Object> param, Map<String, Object> validparam, ValidContext vc, String msg) {
        boolean bb = true;
        for(String pkey : param.keySet()){
            if(!pkey.contains("_eqValue")){//真实key
                String value = this.getString(param,pkey);
                if(value == null) return true;
                String eqValue = this.getString(param,pkey+"_eqValue");
                if(isInStr(value,eqValue)){
                    bb = false;
                    break;
                }
            }
        }
        String field = this.getFirstKey(validparam);
        List list  = this.getList(validparam, field);
        if(bb) {
            if (null != list && list.size()>0) {
                return true;
            } else {
                vc.addError(field, msg);
                return false;
            }
        }else{
            return true;
        }
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
