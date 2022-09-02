package com.github.mg0324.clazz.executor;

/**
 * HotSwap类加载器
 */
public class HotSwapClassLoader extends ClassLoader {
    public HotSwapClassLoader(){
        // 设置父类加载器为加载了HotSwapClassLoader的类加载器，应该是ApplicationClassLoader
        super(HotSwapClassLoader.class.getClassLoader());
    }

    /**
     * 从字节数组数据加载为class
     * @param data
     * @return
     */
    public Class loadByte(byte[] data){
        return defineClass(null,data,0,data.length);
    }
}
