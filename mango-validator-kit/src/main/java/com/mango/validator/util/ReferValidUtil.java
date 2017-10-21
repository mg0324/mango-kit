package com.mango.validator.util;


import com.alibaba.fastjson.JSONObject;
import com.mango.validator.anno.Refer;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by meigang on 17/9/15.
 */
public class ReferValidUtil extends BaseValidUtil{

    public ReferValidUtil(ValidContext validContext){
        this.validContext = validContext;
    }


    public void validRefer(Object obj,Class<?>[] groups,boolean isFailFast,boolean setMap){
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
                                validRefer(list.get(i),groups,isFailFast,false);//list中的对象是无法跨级依赖的，不需要存到map容器
                            }
                        }
                    }else{//对象类型
                        validRefer(getM.invoke(obj),groups,isFailFast,true);
                    }
                }
                //2.解析@Refer注解，同级依赖判断
                if (f.isAnnotationPresent(Refer.class)) {
                    Refer refer = f.getAnnotation(Refer.class);
                    Class[] annoGroups = refer.groups();
                    //支持group 分组
                    if(groups == null || findTheGroup(groups,annoGroups)){
                        //进入验证
                        //打开访问private的属性
                        f.setAccessible(true);
                        Class validator = refer.validator();
                        //反射validate方法
                        Method validateMethod = validator.getMethod("validate", Map.class,Map.class,ValidContext.class,String.class);
                        Map<String, Object> param = warpMap(obj, clazz, refer.param(),f,false);
                        Map<String, Object> validparam = warpMap(obj, clazz, refer.validparam(),f,true);
                        Object b = validateMethod.invoke(validator.newInstance(), param,validparam,validContext,refer.message());
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
    private Map<String, Object> warpMap(Object obj, Class clazz,String[] paramArr,Field field,boolean isValidParam) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Map<String, Object> param = new HashMap<String,Object>();
        if(isValidParam && paramArr.length==0){
            //设置默认的验证值，为注解上值
            Method getM = clazz.getMethod("get" + upperFirst(field.getName()));
            param.put(field.getName(),getM.invoke(obj));
        }else{//参数值
            for (String str : paramArr) {
                String[] p = str.split(":");
                String mStr = p[1];
                if(p[1].contains("=")){
                    mStr = p[1].split("=")[0];
                }
                if(!p[0].equals(obj.getClass().getName())){
                    //跨bean,则从threadMap中获取跨的bean的obj
                    obj = this.validContext.get(p[0]);
                }
                Class pClazz = Class.forName(p[0]);
                Method getM = pClazz.getMethod("get" + upperFirst(mStr));
                if(isValidParam){
                    param.put(p[1], getM.invoke(obj));
                }else{
                    if(p[1].contains("=")){
                        param.put(p[1].split("=")[0]+"_eqValue", p[1].split("=")[1]);
                        param.put(p[1].split("=")[0],getM.invoke(obj));
                    }else{
                        param.put(p[1], getM.invoke(obj));
                        param.put(p[1]+"_eqValue", "1");
                    }
                }
            }
        }
        logger.info("class:"+clazz.getName()+(isValidParam ? "-validparam:" : "-param:")+ JSONObject.toJSONString(param));
        return param;
    }

}
