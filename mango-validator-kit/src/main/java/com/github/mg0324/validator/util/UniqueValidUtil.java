package com.github.mg0324.validator.util;


import com.github.mg0324.validator.handler.UniqueValidator;
import com.github.mg0324.validator.anno.Unique;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by meigang on 17/9/15.
 */
public class UniqueValidUtil extends BaseValidUtil{

    public UniqueValidUtil(ValidContext validContext){
        this.validContext = validContext;
    }

    public void validUnique(Object obj, Class<?>[] groups, boolean isFailFast, boolean setMap, MyValidator uniqueValidator){
        try {
            if(obj == null) return ;
            //只让对象属性保存到map容器中
            if(setMap && !this.validContext.existKey(obj.getClass().getName())) {
                this.validContext.add(obj.getClass().getName(), obj);
            }
            Class clazz = Class.forName(obj.getClass().getName());
            List<Field> fieldList = getAllFields(clazz);
            for (Field f : fieldList) {
                //1.解析@Valid,下钻bean
                if(f.isAnnotationPresent(Valid.class)){
                    Method getM = clazz.getMethod("get"+upperFirst(f.getName()));
                    if(f.getType().getName().equals("java.util.List")){
                        //集合下钻
                        List<Object> list = (List) getM.invoke(obj);
                        if(list!=null) {
                            for (int i = 0; i < list.size(); i++) {
                                validUnique(list.get(i),groups,isFailFast,false,uniqueValidator);//list中的对象是无法跨级依赖的，不需要存到map容器
                            }
                        }
                    }else{//对象类型
                        validUnique(getM.invoke(obj),groups,isFailFast,true,uniqueValidator);
                    }
                }
                //2.解析@Unique注解，同级依赖判断
                if (f.isAnnotationPresent(Unique.class)) {
                    Unique unique = f.getAnnotation(Unique.class);
                    Class[] annoGroups = unique.groups();
                    //支持group 分组
                    if(groups == null || findTheGroup(groups,annoGroups)){
                        //进入验证
                        //打开访问private的属性
                        f.setAccessible(true);
                        //反射validate方法
                        UniqueValidator validator = (UniqueValidator) uniqueValidator;
                        Method validateMethod = validator.getClass().getMethod("validate",UniqueValidator.class, String.class,Map.class,ValidContext.class,String.class);
                        String mapperMethod = unique.mapperMethod();
                        Map<String, Object> sqlparam = warpMap(obj, unique.sqlparam(),f);
                        Object b = validateMethod.invoke(validator,validator,mapperMethod,sqlparam,validContext,unique.message());
                        if(isFailFast) {//是否failfast模式
                            if (b.toString().equals("true")) {
                                //验证成功
                                continue;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            //System.out.println("threadMap:"+this.threadMap.show());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //封装获取配置的参数
    private Map<String, Object> warpMap(Object obj,String[] paramArr,Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Map<String, Object> param = new HashMap<String,Object>();
        for (String str : paramArr) {
            String[] p = str.split(":");

            if(!p[0].equals(obj.getClass().getName())){
                //跨bean,则从threadMap中获取跨的bean的obj
                obj = this.validContext.get(p[0]);
            }
            Class pClazz = obj.getClass();
            Method getM = pClazz.getMethod("get" + upperFirst(p[1]));
            param.put(p[1], getM.invoke(obj));
        }
        return param;
    }


}
