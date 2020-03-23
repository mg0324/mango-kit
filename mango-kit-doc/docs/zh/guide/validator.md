# mango-validator-kit
## 起因
在实现接口时，特别是保存或者更新接口，需要对入库数据做校验，并给予正确的反馈信息。

## 思路
整合`fluent-validator`框架，站在巨人(`hibernate-validator`和`fluent-validator`)肩膀上，再加入一些常用的自定义封装(`@Dict`,`@DictMutil`,`@Refer`)，进而得到Java后台强大的bean校验能力。

## hibernate-validator的能力
以下是常用注解
``` 
@AssertTrue	用于boolean字段，该字段只能为true  
@AssertFalse	该字段的值只能为false
@CreditCardNumber	对信用卡号进行一个大致的验证
@DecimalMax	只能小于或等于该值
@DecimalMin	只能大于或等于该值
@Digits(integer=,fraction=)	检查是否是一种数字的整数、分数,小数位数的数字
@Email	检查是否是一个有效的email地址
@Future	检查该字段的日期是否是属于将来的日期
@Length(min=,max=)	检查所属的字段的长度是否在min和max之间,只能用于字符串
@Max	该字段的值只能小于或等于该值
@Min	该字段的值只能大于或等于该值
@NotNull	不能为null
@NotBlank	不能为空，检查时会将空格忽略
@NotEmpty	不能为空，这里的空是指空字符串
@Null	检查该字段为空
@Past	检查该字段的日期是在过去
@Pattern(regex=,flag=)	被注释的元素必须符合指定的正则表达式
@Range(min=,max=,message=)	被注释的元素必须在合适的范围内
@Size(min=, max=)	检查该字段的size是否在min和max之间，可以是字符串、数组、集合、Map等
@URL(protocol=,host,port)	检查是否是一个有效的URL，如果提供了protocol，host等，则该URL还需满足提供的条件
@Valid	该注解主要用于字段为一个包含其他对象的集合或map或数组的字段，或该字段直接为一个其他对象的引用，这样在检查当前对象的同时也会检查该字段所引用的对象
```
## fluent-validator的能力
[fluent-validator文档](http://neoremind.com/2016/02/java%E7%9A%84%E4%B8%9A%E5%8A%A1%E9%80%BB%E8%BE%91%E9%AA%8C%E8%AF%81%E6%A1%86%E6%9E%B6fluent-validator/)

## mango-validator-kit的能力
### @Dict
拓展自`javax.validation`的`ConstraintValidator`接口，可验证值须在某些值之内，并且只能是其中一个值。
如：
``` java 
@Dict(allowValue = "白猫,黑猫,花猫",message = "猫的类型不合法")
private String type;
```
### @DictMulti
拓展自`javax.validation`的`ConstraintValidator`接口，可验证值须在某些值之内，并且可以是其中多个值。
如：
``` java 
@DictMulti(allowValue = "白猫,黑猫,花猫",message = "猫的类型不合法")
private String type;
```
### @Refer

``` java 
@Refer(validator = TaskMethodValidator.class,
        param = {},
        validparam = {"com.github.mg0324.bean.Cat:taskClass",
                "com.github.mg0324.bean.Cat:taskMethod"},
        message = "执行方法不存在")
private String taskMethod;
```

## 快速上手
### 添加依赖
``` xml
<dependency>
    <groupId>com.github.mg0324</groupId>
    <artifactId>mango-validator-kit</artifactId>
    <version>${mango.kit.version}</version>
</dependency>
```
### 给bean添加注解
可以是`hibernate-validator`的注解，可以是`fluent-validator`的注解，也可以是`mango-validator-kit`的注解。

如下列子：
``` java 
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

    @NotBlank(message = "jsonStr不能为空")
    @FluentValidate({CatNameValidator.class})
    private String jsonStr;

    @NotBlank(message = "执行类不能为空")
    @FluentValidate({TaskClassValidator.class})
    private String taskClass;

    @NotBlank(message = "执行方法不能为空")
    @Refer(validator = TaskMethodValidator.class,
            param = {},
            validparam = {"com.github.mg0324.bean.Cat:taskClass",
                    "com.github.mg0324.bean.Cat:taskMethod"},
            message = "执行方法不存在")
    private String taskMethod;

}
```
### 调用`ValidUtil.validateAll`来完成校验
``` java 
List<String> list = ValidUtil.validateAll(cat,false);
System.out.println(JSONObject.toJSONString(list));
```

::: tip
当现有的注解无法满足bean字段的校验时，可以考虑扩展校验来完成。

例如`@Dict`的实现，将会有`hibernate-validator`完成校验。

也可以扩展`fluent-validator`的校验器，例如`TaskClassValidator`。

如果是校验的字段需要依赖其他的字段值，则需要扩展`@Refer`来实现。
:::

