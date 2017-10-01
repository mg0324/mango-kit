package com.mango.validator.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by meigang on 17/9/15.
 */
public class ValidContext {
    private Map<String,Object> errorMap;
    //线程安全的map
    private ConcurrentMap<String,Object> nmap;

    public ValidContext(){
        errorMap = new HashMap<String,Object>();
        nmap = new ConcurrentHashMap<String,Object>();
    }

    public Map<String, Object> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<String, Object> errorMap) {
        this.errorMap = errorMap;
    }

    public void addError(String key,String msg) {
        if(this.errorMap.containsKey(key)){
            key = key + System.currentTimeMillis();//list集合多个对象都出现一个校验错误，提示会被覆盖，在failover模式下
        }
        this.errorMap.put(key,msg);
    }

    public boolean isSuccess(){
        if(errorMap.size()>0){
            return false;
        }else{
            return true;
        }
    }

    public void add(String key,Object value){
        nmap.put(key,value);
    }

    public Object get(String key) {
        return nmap.get(key);
    }


    public String show(){
        return nmap.toString();
    }

    public boolean existKey(String name) {
        if(nmap.containsKey(name)){
            return true;
        }else{
            return false;
        }
    }
}
