package com.mango.time.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by meigang on 17/10/1.
 */
public class TimeKit {
    /**
     *
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

}
