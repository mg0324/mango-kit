package com.mango.bean;

import com.mango.validator.anno.Dict;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import java.io.Serializable;

@Data
public class Cat implements Serializable {


    @NotBlank(message = "id不能为空")
    private String id;

    @Range(min = 2,max = 20,message = "名称须要2-20个字符")
    private String name;

    @Range(min = 1,max = 120,message = "年龄需在1-120岁之间")
    private int age;

    @NotBlank(message = "猫的类型不能为空")
    @Dict(allowValue = "白猫,黑猫,花猫",message = "猫的类型不合法")
    private String type;


}
