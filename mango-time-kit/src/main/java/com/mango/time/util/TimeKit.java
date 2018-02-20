package com.mango.time.util;

import com.mango.time.TimeCglib;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by meigang on 17/10/1.
 */
public class TimeKit {
    /**
     * 使用反射来加入调用真实方法的耗时监控，但其实并不准确
     * @param obj 调用对象
     * @param methodName 方法名
     * @param params 参数
     */
    public static Object invoke(Object obj,String methodName,Class[] paramTypes,Object... params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = obj.getClass().getMethod(methodName,paramTypes);
        long start = System.currentTimeMillis();
        Object ret = method.invoke(obj,params);
        long end = System.currentTimeMillis();
        System.out.println(obj.getClass().getName()+"."+methodName+"耗时:"+((double)(end-start)/1000)+"s");
        return ret;
    }

    /**
     * 使用cglib来做加强，是不有
     * @param obj 调用对象
     * @return 返回动态代理cglib生成的子类对象
     */
    public static Object invokeCglib(Object obj) {
        TimeCglib timeCglib = new TimeCglib();
        return timeCglib.getInstance(obj);
    }

}
