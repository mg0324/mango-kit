package com.mango.test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;


public class A {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        System.out.println("result="+(a+b));

        MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        System.out.println(bean.getHeapMemoryUsage().toString());
    }
}
