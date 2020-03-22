package com.github.mg0324.time;

import com.github.mg0324.time.util.TimeKit;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
/**
 * Created by meigang on 17/10/1.
 */
public class TimeTest {
    //测试反射
    @Test
    public void testReflect() throws Exception {
        Cat cat = new Cat();
        Object ret = TimeKit.invoke(cat,"cry",new Class[]{String.class,int.class},"反射小刚",24);
        System.out.println(ret);
    }

    /**
     * 反射测试结果：
     * 我是一只小猫猫,名字叫做反射小刚
     我今年24岁啦
     Cat.cry耗时:0.002s
     ok
     */

    //测试cglib
    @Test
    public void testCglib() {
        Cat catProxy = (Cat) TimeKit.invokeCglib(new Cat());
        catProxy.cry("cglib小猫",20);
    }
    /**
     * cglib测试结果：
     * 我是一只小猫猫,名字叫做cglib小猫
     我今年20岁啦
     Cat$$EnhancerByCGLIB$$f8cab545.cry耗时:0.016s
     */


    //测试手动加入
    @Test
    public void test() {
        Cat cat = new Cat();
        long start = System.currentTimeMillis();
        cat.cry("小猫",20);
        long end = System.currentTimeMillis();
        System.out.println(cat.getClass().getName()+".cry"+"耗时:"+(end-start)+"ms");
    }
    /**
     * 测试结果：
     * 我是一只小猫猫,名字叫做小猫
     我今年20岁啦
     Cat.cry耗时:0.001s
     */
}
