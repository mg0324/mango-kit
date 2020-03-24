# mango-fast-kit
## 起因
当发觉某些处理逻辑很慢，以至于影响正常业务时，则需要对其加速。

## 场景
1. 有一张表A(a,b)有100万数据，需要`set a='1'`。
2. 某个队列每分钟有1万消息堆积，需要快速消费。

::: tip
传统方式则是执行一条sql，优化消费者逻辑。

实际上可以对无顺序业务做分页（按hash，或者时间搓），然后多线程并行处理，则能加速。
:::

## 使用案例
### 添加依赖
``` xml
<!-- https://mvnrepository.com/artifact/com.github.mg0324/mango-fast-kit -->
<dependency>
    <groupId>com.github.mg0324</groupId>
    <artifactId>mango-fast-kit</artifactId>
    <version>${mango.kit.version}</version>
</dependency>
```

### 模拟业务逻辑
1. 实现加速接口`IFast`
``` java 
public class Busi implements IFast {

    @Override
    public Object invoke(FastKit fastKit, List<ListenableFuture<FastResult>> list){
        //提交业务到多线程任务队列中
        fastKit.submit(list,new Busi());
        fastKit.submit(list,new Busi());
        fastKit.submit(list,new Busi());
        fastKit.submit(list,new Busi());
        fastKit.submit(list,new Busi());
        fastKit.submit(list,new Busi());
        return null;
    }

    @Override
    public FastResult call() throws Exception {
        //callable主业务逻辑
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName());
        return FastKit.success(UUID.randomUUID().toString());
    }
}
```
2. 测试类

加入timeKit输出方法耗时
``` java 
public class TheTest {

    public static void main(String[] args) {
        TheTest theTest = new TheTest();
        theTest = (TheTest) TimeKit.invokeCglib(theTest);
        theTest.test();
    }

    public void test(){
        //业务逻辑对象
        Busi busi = new Busi();
        //创建加速工具
        FastKit fastKit = new FastKit();
        //获取8线程加速后的业务逻辑增强对象
        Busi fastBusi = (Busi) fastKit.getProxy(busi,8);
        List<ListenableFuture<FastResult>> list = Lists.newArrayList();
        Map map = (Map) fastBusi.invoke(fastKit,list);
        System.out.println(JSONObject.toJSONString(map));
    }
}
```

3. 测试输出
``` java 
pool-1-thread-1
pool-1-thread-4
pool-1-thread-5
pool-1-thread-6
pool-1-thread-3
pool-1-thread-2
{"0ca31df8-ad95-4e7c-a230-5c21d094cbed":{"id":"0ca31df8-ad95-4e7c-a230-5c21d094cbed","state":1},"d63b8823-3c8b-43d9-b864-aac5972ad0ec":{"id":"d63b8823-3c8b-43d9-b864-aac5972ad0ec","state":1},"3704dd43-b6b8-4a35-91e4-a21dc6cdee24":{"id":"3704dd43-b6b8-4a35-91e4-a21dc6cdee24","state":1},"0e031352-a95b-4392-9510-cf00b1508bc0":{"id":"0e031352-a95b-4392-9510-cf00b1508bc0","state":1},"959583c3-9fb8-4683-a06f-6e10da4825e6":{"id":"959583c3-9fb8-4683-a06f-6e10da4825e6","state":1},"cc7e62f5-315c-41db-93c4-46e4f7265047":{"id":"cc7e62f5-315c-41db-93c4-46e4f7265047","state":1}}
12:04:25.873 [main] DEBUG com.github.mg0324.time.TimeCglib - com.github.mg0324.fast.test.TheTest$$EnhancerByCGLIB$$c9fb7e9c.test耗时:1515ms
```
