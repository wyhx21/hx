package org.layz.hx.core.pojo.info;

import org.layz.hx.base.annotation.HxPoiFile;

import java.util.List;

public class PoiFilenfo {
    private HxPoiFile poiFile;
    private String sheetName;
    private String titleName;
    private int totalCol;
    private List<PoiColumnInfo> columnInfos;

    public HxPoiFile getPoiFile() {
        return poiFile;
    }

    public void setPoiFile(HxPoiFile poiFile) {
        this.poiFile = poiFile;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<PoiColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<PoiColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }

    public int getTotalCol() {
        return totalCol;
    }

    public void setTotalCol(int totalCol) {
        this.totalCol = totalCol;
    }
}
