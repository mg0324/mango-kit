package com.mango.bean;

import com.alibaba.fastjson.JSONObject;
import com.github.mg0324.validator.util.ValidUtil;
import org.junit.Test;

import java.util.List;

public class TestCat {

    @Test
    public void test1(){
        Cat cat = new Cat();
        List<String> list = ValidUtil.validateAll(cat,false);
        System.out.println(JSONObject.toJSONString(list));
    }

}
