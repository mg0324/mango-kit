package com.mango.time;

/**
 * Created by meigang on 17/10/1.
 */
public class Cat {

    public String cry(String name,int age) throws InterruptedException {
        System.out.println("我是一只小猫猫,名字叫做"+name);
        System.out.println("我今年"+age+"岁啦");
        return "ok";
    }
}
