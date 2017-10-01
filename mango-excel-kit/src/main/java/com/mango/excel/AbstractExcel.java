package com.mango.excel;

import com.mango.excel.vo.ValueCell;
import com.mango.excel.vo.ValueExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by meigang on 17/9/22.
 * 抽象excel类
 */
public abstract class AbstractExcel {

    /**
     * 导出简单行数据的excel，适用于模板表头一行，数据单行，无合并的简单表格
     * @param xlsTemplatePath xls模板路径(classpath中),如放在src/main/resources
     * @param datalist list<行数据>
     * @param desXlsPath 输出xls文件路径
     */
    public void exportRowExcel(String xlsTemplatePath, List<List<Object>> datalist, String desXlsPath) throws Exception {
        List<ValueExcel> valueExcelList = new ArrayList<ValueExcel>();
        ValueExcel valueExcel = new ValueExcel();
        valueExcel.setSheetIndex(0);
        List<ValueCell> valueCellList = new ArrayList<ValueCell>();
        for(int i=0;i<datalist.size();i++){
            for(int j=0;j<datalist.get(i).size();j++){
                ValueCell cell = new ValueCell(i+1,j,datalist.get(i).get(j));
                valueCellList.add(cell);
            }
        }
        valueExcel.setValueCellList(valueCellList);
        valueExcelList.add(valueExcel);
        this.exportGridExcel(xlsTemplatePath,valueExcelList,desXlsPath);
    }

    /**
     * 导出复查的方格excel，适用于所有excel模板
     * 通过模板+数据，生成excel
     * @param xlsTemplatePath xls模板路径(classpath中),如放在src/main/resources
     * @param valueExcelList 用于指定sheet中的坐标定位及值
     * @param desXlsPath 输出xls文件路径
     */
    public void exportGridExcel(String xlsTemplatePath, List<ValueExcel> valueExcelList, String desXlsPath) throws Exception{
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(xlsTemplatePath);
        HSSFWorkbook templateWb = new HSSFWorkbook(inputStream);
        for(ValueExcel valueExcel : valueExcelList){
            //得到sheet
            Sheet sheet = templateWb.getSheetAt(valueExcel.getSheetIndex());
            for(ValueCell vc : valueExcel.getValueCellList()){
                Row row = sheet.getRow(vc.getRow());
                if(row == null){
                    //模板中无行
                    row = sheet.createRow(vc.getRow());
                }
                Cell cell = row.getCell(vc.getCol());
                if(cell == null){
                    cell = row.createCell(vc.getCol());
                }
                Object obj = vc.getValue();
                // 判断对象所属类型, 并强转
                if (obj instanceof Integer) // 当数字时
                    cell.setCellValue((Integer) obj);
                if (obj instanceof String) // 当为字符串时
                    cell.setCellValue((String) obj);
                if (obj instanceof Boolean) // 当为布尔时
                    cell.setCellValue((Boolean) obj);
                if (obj instanceof Date) // 当为时间时
                    cell.setCellValue((Date) obj);
                if (obj instanceof Calendar) // 当为时间时
                    cell.setCellValue((Calendar) obj);
                if (obj instanceof Double) // 当为小数时
                    cell.setCellValue((Double) obj);
            }
        }
        this.export(templateWb,desXlsPath);
    }

    /**
     * 导出到excel文件
     * @param wb 组装好的excel文档对象
     * @param filePath 要保存的文件地址
     * @throws Exception
     */
    private void export(HSSFWorkbook wb, String filePath) throws Exception {
        FileOutputStream fileOutputStream = null;
        try {
            // 根据指定xls文件创建文件字符流
            fileOutputStream = new FileOutputStream(filePath);
            // 将文档写入指定文件
            wb.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流, 释放资源
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
