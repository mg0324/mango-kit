package com.mango.test;

import com.github.mg0324.clazz.executor.JavaClassExecutor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class TheTest {
    public static void main(String[] args) throws IOException {
        String classFile = "/Users/mango/git/mango-kit/mango-clazz-executor-kit/target/test-classes/com/mango/test/A.class";
        InputStream is = new FileInputStream(new File(classFile));
        byte[] data = new byte[is.available()];
        is.read(data);
        is.close();
        String str = JavaClassExecutor.execute(data);
        System.out.println(str);
    }
}
