package com.github.mg0324.time.util;

import com.github.mg0324.time.TimeCglib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by meigang on 17/10/1.
 */
public class TimeKit {
    static Logger logger = LoggerFactory.getLogger(TimeKit.class);
    /**
     * 使用反射来加入调用真实方法的耗时监控，但其实并不准确
     * @param obj 调用对象
     * @param methodName 方法名
     * @param paramTypes 方法参数类型
     * @param params 参数
     * @return 调用方法的返回值
     * @throws Exception 执行异常
     */
    public static Object invoke(Object obj,String methodName,Class[] paramTypes,Object... params) throws Exception {
        Method method = obj.getClass().getMethod(methodName,paramTypes);
        long start = System.currentTimeMillis();
        Object ret = method.invoke(obj,params);
        long end = System.currentTimeMillis();
        logger.debug(obj.getClass().getName()+"."+methodName+"耗时:"+((double)(end-start)/1000)+"s");
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
