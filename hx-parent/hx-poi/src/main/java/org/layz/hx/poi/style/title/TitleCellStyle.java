package org.layz.hx.poi.style.title;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.core.pojo.info.PoiColumnInfo;

public interface TitleCellStyle {
    /**
     * @return
     */
    int type();

    /**
     * @param wookBook
     */
    void setWookBook(Workbook wookBook);
    /**
     * @return
     */
    CellStyle getTitleStyle();

    /**
     * @return
     */
    CellStyle getHeadleStyle(PoiColumnInfo poiColumnInfo);
}
