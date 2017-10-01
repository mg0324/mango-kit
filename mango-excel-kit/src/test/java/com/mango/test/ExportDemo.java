package com.mango.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mango.excel.AbstractExcel;
import com.mango.excel.vo.ValueCell;
import com.mango.excel.vo.ValueExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meigang on 17/9/22.
 */
public class ExportDemo extends AbstractExcel {
    String saveDir = "/Users/meigang/fpdir";
    /**
     * 测试导出
     */
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
}
