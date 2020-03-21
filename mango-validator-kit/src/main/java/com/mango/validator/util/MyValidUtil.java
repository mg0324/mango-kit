package com.mango.validator.util;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by meigang on 17/9/15.
 */
public class MyValidUtil{
    //局部变量，跟随myValidUitl对象，在一次验证中有效，多个验证间存储不冲突
    private ValidContext validContext;


    public MyValidUtil(){
        validContext = new ValidContext();
    }

    public void validUnique(Object obj, Class<?>[] groups, boolean isFailFast, boolean setMap, MyValidator uniqueValidator){
        UniqueValidUtil uniqueValidUtil = new UniqueValidUtil(this.validContext);
        uniqueValidUtil.validUnique(obj,groups,isFailFast,setMap,uniqueValidator);
    }

    public void validRefer(Object obj,Class<?>[] groups,boolean isFailFast,boolean setMap){
        ReferValidUtil referValidUtil = new ReferValidUtil(this.validContext);
        referValidUtil.validRefer(obj,groups,isFailFast,setMap);
    }


    public List<String> getMsgList(){
        if(!validContext.isSuccess()){
            List<String> list = new ArrayList<String>();
            for(String key : validContext.getErrorMap().keySet()){
                list.add(validContext.getErrorMap().get(key).toString());
            }
            return list;
        }else{
            return null;
        }
    }

}
