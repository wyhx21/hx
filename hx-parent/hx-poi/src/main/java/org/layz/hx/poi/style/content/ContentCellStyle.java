package org.layz.hx.poi.style.content;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.core.pojo.info.PoiColumnInfo;

public interface ContentCellStyle {
    /**
     *
     * @return
     */
    int type();

    /**
     * @param workBook
     */
    void setWorkBook(Workbook workBook);
    /**
     * @param rowIndex
     * @param obj
     * @param poiColumnInfo
     * @return
     */
    CellStyle getCellStyle(int rowIndex, Object obj, PoiColumnInfo poiColumnInfo);
}
