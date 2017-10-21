package com.mango.validator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meigang on 17/10/21.
 */
public class BaseValidUtil {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    //局部变量，跟随myValidUitl对象，在一次验证中有效，多个验证间存储不冲突
    protected ValidContext validContext;
    /**
     * 获取包含父类的所有属性
     * @param clazz
     * @return
     */
    protected List<Field> getAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<Field>();
        Class tempClass = clazz;
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }
    protected String upperFirst(String name) {
        return name.substring(0,1).toUpperCase().concat(name.substring(1,name.length()));
    }

    public boolean findTheGroup(Class<?>[] validGroups,Class<?>[] annoGroups){
        for(int i=0;i<validGroups.length;i++){
            Class clazz = validGroups[i];
            for(int j=0;j<annoGroups.length;j++){
                Class annoClazz = annoGroups[j];
                if(clazz.getName().equals(annoClazz.getName())){
                    //是相同组
                    return true;
                }
            }
        }
        return false;
    }
}
