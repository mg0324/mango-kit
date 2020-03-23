# mango-excel-kit
## 起因
在日常工作中，特别是电子政务行业，都会需要导出数据到excel中，方便整理和汇报。

## 思路
`模板+数据=输出Excel`，采用的是模板的思路。
* 1.基于POI，实现java excel导出文档工具。
* 2.基于xls模板，填写动态数据思想。（单行数据填入和方格数据填入）

## 提供工具
### exportRowExcel
封装常用的简单行导出excel的方法，`com.github.mg0324.excel.AbstractExcel.exportRowExcel`
### exportGridExcel
封装方格数据填充导出excel的方法，`com.github.mg0324.excel.AbstractExcel.exportGridExcel`

## 使用案例
1. 添加依赖
``` xml
<!-- https://mvnrepository.com/artifact/com.github.mg0324/mango-excel-kit -->
<dependency>
    <groupId>com.github.mg0324</groupId>
    <artifactId>mango-excel-kit</artifactId>
    <version>${mango.kit.version}</version>
</dependency>
```

2. 继承`AbstractExcel`，获得导出row和grid的能力
``` java
public class ExportDemo extends AbstractExcel 
```

3. 按行填充数据导出excel
``` java
@Test
public void test2() throws Exception {
    String xlsTemplatePath = "xls/demo.xls";
    List<List<Object>> datalist = new ArrayList<List<Object>>();
    String data = "[{\"sex\":\"男\",\"age\":24,\"name\":\"小刚\"},{\"sex\":\"男\",\"age\":24,\"name\":\"小刚\"},{\"sex\":\"男\",\"age\":24,\"name\":\"小刚\"}]";
    JSONArray jsonArr = JSONObject.parseArray(data);
    for(int i=0;i<jsonArr.size();i++){
        JSONObject json = jsonArr.getJSONObject(i);
        List<Object> list = new ArrayList<Object>();
        list.add(json.getString("name"));
        list.add(json.getIntValue("age"));
        list.add(json.getString("sex"));
        datalist.add(list);
    }
    String desXlsPath = saveDir + "/" + System.currentTimeMillis()+".xls";
    this.exportRowExcel(xlsTemplatePath,datalist,desXlsPath);
}
```

4. 按方格填充数据导出excel
``` java 
@Test
public void test1() throws Exception {
    String xlsTemplatePath = "xls/demo.xls";
    List<ValueExcel> valueExcelList = new ArrayList<ValueExcel>();
    ValueExcel ve = new ValueExcel();
    ve.setSheetIndex(0);
    List<ValueCell> valueCellList = new ArrayList<ValueCell>();
    String data = "[{\"sex\":\"男\",\"age\":24,\"name\":\"小刚\"},{\"sex\":\"男\",\"age\":24,\"name\":\"小刚\"},{\"sex\":\"男\",\"age\":24,\"name\":\"小刚\"}]";
    JSONArray jsonArr = JSONObject.parseArray(data);
    for(int i=0;i<jsonArr.size();i++){
        JSONObject json = jsonArr.getJSONObject(i);
        ValueCell vc1 = new ValueCell(i+1,0,json.getString("name"));
        ValueCell vc2 = new ValueCell(i+1,1,json.getIntValue("age"));
        ValueCell vc3 = new ValueCell(i+1,2,json.getString("sex"));
        valueCellList.add(vc1);
        valueCellList.add(vc2);
        valueCellList.add(vc3);
    }
    ve.setValueCellList(valueCellList);
    valueExcelList.add(ve);
    String desXlsPath = saveDir + "/" + System.currentTimeMillis()+".xls";
    this.exportGridExcel(xlsTemplatePath,valueExcelList,desXlsPath);
}
```


