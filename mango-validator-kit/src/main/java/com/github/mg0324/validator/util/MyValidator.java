package com.github.mg0324.validator.util;

import java.util.List;
import java.util.Map;

/**
 * Created by meigang on 17/9/15.
 */
public abstract class MyValidator {

    protected String getString(Map<String,Object> map, String key){
        Object obj = map.get(key);
        if(null == obj){
            return null;
        }else{
            return obj.toString();
        }
    }

    //得到map中的key的list对象，如果为空，则返回null
    protected List getList(Map<String,Object> map, String key){
        Object obj = map.get(key);
        if(null == obj){
            return null;
        }else{
            return (List) obj;
        }
    }

    //判断字符串不为空
    protected boolean isNotEmpty(String str){
        if(str!=null){
            return true;
        }else{
            return false;
        }
    }

    //判断字符串不为空，并且length大于0
    protected boolean isNotBlank(String str){
        if(str!=null && str.length()>0){
            return true;
        }else{
            return false;
        }
    }

    public String getFirstKey(Map<String, Object> validparam){
        String retkey = null;
        for(String key : validparam.keySet()){
            if(key.contains("_eqValue")){
                continue;
            }else{
                retkey = key;
                break;
            }
        }
        return retkey;
    }
}
