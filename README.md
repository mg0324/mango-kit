# mango-kit
mango mei的工具集

* 为顶级 Module 设置新版本
```
mvn vsersion:set -DnewVersion=1.1-SANPSHOT
```
* 更新所有子 Module 的版本
```
mvn versions:update-child-modules
```

详细文档见wiki - http://mg.meiflower.top/mango-kit   

## mango-validator-kit 
* 集成fluent-validator,hibernate-validator，并实现特定业务场景的后台验证框架工具。

## mango-time-kit 
* 实现Java方法运行耗时监控工具。
* 2中实现方式，反射执行及cglib动态代理执行。
* 直接执行是最快的，反射执行其次，cglib最慢。不过一样能达到监控方法运行耗时效果，推荐使用反射执行。

## mango-excel-kit
* 1.基于POI，实现java excel导出文档工具。
* 2.基于xls模板，填写动态数据思想。（单行数据填入和方格数据填入）