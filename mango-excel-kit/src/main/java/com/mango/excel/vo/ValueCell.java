package com.mango.excel.vo;

/**
 * Created by meigang on 17/9/22.
 * excel导出中使用到的格子值
 */
public  class ValueCell {
    private int row;
    private int col;
    private Object value;

    public ValueCell(int row,int col,Object value){
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}