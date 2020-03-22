# mango-time-kit
## 起因
很多时候，我们希望能够知晓某个方法执行耗时时间。

``` java
long start = System.currentTimeMillis();
dosomething();
long end = System.currentTimeMillis();
logger.debug("耗时:"+(end-start)+"ms");
```
## 场景
要知晓段代码的执行耗时，可以将其封装为方法，并加入end-start块。如此重复的代码，虽少但是还是封装为mango-time-kit轮子。
::: tip
如是在某段代码前后加入一些操作的场景，可以使用Java动态代理来实现切面编程；

mango-time-kit就是引入cglib来实现类的动态代理，进而完成方法的耗时debug输出。
:::

## 使用案例
1. 传统方式
``` java
@Test
public void test() {
    Cat cat = new Cat();
    long start = System.currentTimeMillis();
    cat.cry("小猫",20);
    long end = System.currentTimeMillis();
    System.out.println(cat.getClass().getName()+".cry"+"耗时:"+(end-start)+"ms");
}
```

2. time增强
``` java
@Test
public void testCglib() {
    Cat catProxy = (Cat) TimeKit.invokeCglib(new Cat());
    catProxy.cry("cglib小猫",20);
}
```
