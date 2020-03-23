package com.github.mg0324.validator.mango.handler.refer;



import com.github.mg0324.validator.mango.handler.ReferValidator;
import com.github.mg0324.validator.util.ValidContext;

import java.util.Map;

/**
 * Created by meigang on 17/9/15.
 */
public class AOneBNotNullValidator extends ReferValidator {

    @Override
    public boolean validate(Map<String, Object> param, Map<String, Object> validparam, ValidContext vc, String msg) {
        String bb = this.getString(param,this.getFirstKey(param));
        String eqValue = this.getString(param,this.getFirstKey(param)+"_eqValue");
        if(null != bb){
            String field = this.getFirstKey(validparam);
            String str = this.getString(validparam, field);
            if(bb.equals(eqValue)) {
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
}
