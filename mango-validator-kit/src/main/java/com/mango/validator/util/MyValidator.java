package com.mango.validator.util;

import java.util.List;
import java.util.Map;

/**
 * Created by meigang on 17/9/15.
 */
public abstract class MyValidator {
    /**
     * 抽象方法，@Refer验证器需要的
     * @param param 条件参数
     * @param validparam 验证参数
     * @param vc 验证上下文
     * @param msg 错误信息
     * @return
     */
    public abstract boolean validate(Map<String, Object> param, Map<String, Object> validparam, ValidContext vc, String msg);

    /**
     * 得到
     * @param map
     * @param key
     * @return
     */
    protected String getString(Map<String,Object> map, String key){
        Object obj = map.get(key);
        if(null == obj){
            return null;
        }else{
            return obj.toString();
        }
    }

    /**
     * 得到map中的key的list对象，如果为空，则返回null
     * @param map
     * @param key
     * @return
     */
    protected List getList(Map<String,Object> map, String key){
        Object obj = map.get(key);
        if(null == obj){
            return null;
        }else{
            return (List) obj;
        }
    }

    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
    protected boolean isNotEmpty(String str){
        if(str!=null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断字符串不为空，并且length大于0
     * @param str
     * @return
     */
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