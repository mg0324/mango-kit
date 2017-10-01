package com.mango.time;

import com.mango.time.util.TimeKit;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by meigang on 17/10/1.
 */
public class TimeTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Cat cat = new Cat();
        Object ret = TimeKit.invoke(cat,"cry",new Class[]{String.class,int.class},"小刚",24);
        System.out.println(ret);
    }
}
