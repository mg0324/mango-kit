package com.mango.excel.vo;

import java.util.List;

/**
 * Created by meigang on 17/9/22.
 * excel值对象
 */
public class ValueExcel {

    private int sheetIndex;

    private List<ValueCell> valueCellList;

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public List<ValueCell> getValueCellList() {
        return valueCellList;
    }

    public void setValueCellList(List<ValueCell> valueCellList) {
        this.valueCellList = valueCellList;
    }
}