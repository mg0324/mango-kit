package com.mango.time;

/**
 * Created by meigang on 17/10/1.
 */
public class Cat {

    public String cry(String name,int age) throws InterruptedException {
        System.out.println("我是一只小猫猫,名字叫做"+name);
        int b = (int) (Math.random() * 10);
        Thread.sleep(b*1000);
        System.out.println("我今年"+age+"岁啦");
        return "ok";
    }
}
