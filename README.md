## mango-kit
Java增强工具集 http://mg.meiflower.top/mango-kit

## 模块说明
* `mango-time-kit` 基于`cglib`增强Java方法运行耗时监控工具。
* `mango-excel-kit` 集成POI，基于xls模板，增强excel导出工具。
* `mango-validator-kit` 集成 `fluent-validator`，并实现特定业务场景的后台验证框架工具。
* `mango-fast-kit` 基于`guava`和`cglib`实现多线程并行加速工具。


## 辅助操作
* 为顶级 Module 设置新版本
```
mvn versions:set -DnewVersion=1.1-SANPSHOT
```
* 更新所有子 Module 的版本
```
mvn versions:update-child-modules
```
