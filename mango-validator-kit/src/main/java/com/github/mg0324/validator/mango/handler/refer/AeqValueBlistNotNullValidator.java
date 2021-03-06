package com.github.mg0324.validator.mango.handler.refer;



import com.github.mg0324.validator.mango.handler.ReferValidator;
import com.github.mg0324.validator.util.ValidContext;

import java.util.List;
import java.util.Map;

/**
 * Created by meigang on 17/9/15.
 */
public class AeqValueBlistNotNullValidator extends ReferValidator {

    @Override
    public boolean validate(Map<String, Object> param, Map<String, Object> validparam, ValidContext vc, String msg) {
        String bb = this.getString(param,this.getFirstKey(param));
        String eqValue = this.getString(param,this.getFirstKey(param)+"_eqValue");
        if(null != bb){
            String field = this.getFirstKey(validparam);
            List list  = this.getList(validparam, field);
            if(bb.equals(eqValue)) {
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
        return true;
    }
}
