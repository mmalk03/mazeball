package com.mmalk.mazeball.maputil;

public class ArrayPosition {

    private int row;
    private int column;

    public ArrayPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setPosition(int row, int column){
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "[" + this.row + "," + this.column + "] ";
    }
}
