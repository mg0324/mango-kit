package com.mango.excel.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @描述 负责将数据集(表单数据)导出Excel文件
 * @author

 */
public class ExcelUtil {

    /**
     * 类实例
     */
    private static ExcelUtil export;

    /**
     * excel sheet
     */
    private HSSFSheet sheet;

    /**
     * 字节流
     */
    private OutputStream fileOutput;

    /**
     * 声明私有构造方法
     */
    private ExcelUtil() {

    }

    /**
     * 产生一个excel导出工具类实例(单例模式)
     *
     * @return excel导出工具类对象
     */
    public static ExcelUtil newInstance() {
        if (export == null)
            export = new ExcelUtil();
        return export;
    }

    /**
     * @功能描述 设置excel表单行数据
     * @创建人
     * @创建时间 2011-5-27 下午02:22:30
     * @param row
     *            excel表单行
     * @param tRow
     *            excel表单行数据
     * @return 设置后的的表单行
     * @throws Exception
     *             异常往外抛出
     */
    private HSSFRow setTRow(HSSFRow row, ArrayList tRow) throws Exception {

        try {
            // 获取单元格样式
            //HSSFCellStyle cellStyle = this.setCellStyle(tHeaderStyle);
            // 声明单元格
            HSSFCell cell = null;

            // for循环完成该表单某行各个列赋值和样式
            for (int i = 0; i < tRow.size(); i++) {
                cell = row.createCell(i); // 获取每列单元格
                //cell.setCellStyle(cellStyle); // 设置样式

                sheet.autoSizeColumn((short) i); // 设置单元格自适应
                Object obj = tRow.get(i); // 获取当前列的值
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row; // 返回
    }


    /**
     * @功能描述 导出Excel
     * @创建人
     * @创建时间 2011-5-27 下午02:57:37
     * @param workbook
     *            excel文档
     * @param filePath
     *            xls文件地址
     * @throws Exception
     *             异常往外抛出
     */
    private void export(HSSFWorkbook workbook, String filePath) throws Exception {

        try {
            // 根据指定xls文件创建文件字符流
            fileOutput = new FileOutputStream(filePath);
            // 将文档写入指定文件
            workbook.write(fileOutput);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流, 释放资源
                fileOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @功能描述 获取流
     * @创建人
     * @创建时间 2011-5-27 下午02:57:37
     * @param workbook
     *            excel文档
     * @throws Exception
     *             异常往外抛出
     */
    private InputStream export(HSSFWorkbook workbook) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            try {
                workbook.write(baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] ba = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
            return bais;
        } finally {
            // 关闭流, 释放资源
            baos.close();
        }
    }

    public void exportExcel(HSSFWorkbook templateWb, List<ArrayList<Object>> rowList, String filePath) {
        try {
            //将数据写入到模板的内存2行
            this.writeRows(templateWb,rowList);
            // 导出excel文件
            this.export(templateWb, filePath);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void writeRows(HSSFWorkbook templateWb, List<ArrayList<Object>> rowList) {
        try {
            // 创建表单并设置其表名
            sheet = templateWb.getSheetAt(0);
            HSSFRow tRow = null;
            // for循环完成表单数据的赋值和样式(除表头)
            System.out.println("导出数据中");
            for (int i = 0; i < rowList.size(); i++) {
                tRow = sheet.createRow(i + 1); // 获取表单行
                this.setTRow(tRow, rowList.get(i)); // 设置当前行的数据和样式
                System.out.print(".");
                if((i+1) % 20 == 0){
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出
     * @param templateWb
     * @param filePath
     */
    public void exportExcel(HSSFWorkbook templateWb, String filePath) {
        // 导出excel文件
        try {
            this.export(templateWb, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}