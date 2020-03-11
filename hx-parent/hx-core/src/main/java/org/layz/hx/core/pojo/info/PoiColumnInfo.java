package org.layz.hx.core.pojo.info;

import org.layz.hx.base.annotation.HxPoiColumn;
import org.layz.hx.base.info.FieldInfo;

public class PoiColumnInfo extends FieldInfo {
    private HxPoiColumn poiColumn;
    private String columnName;
    private String title;
    private int sort;
    private int width;
    private int cols;

    public HxPoiColumn getPoiColumn() {
        return poiColumn;
    }

    public void setPoiColumn(HxPoiColumn poiColumn) {
        this.poiColumn = poiColumn;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
