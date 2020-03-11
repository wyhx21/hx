package org.layz.hx.poi.style.content;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.base.inte.poi.CellStyleType;
import org.layz.hx.base.inte.poi.ContentStyle;
import org.layz.hx.core.pojo.info.PoiColumnInfo;
import org.layz.hx.poi.factory.CellStyleFactory;

public class DefaultContentStyle implements ContentCellStyle{
    private CellStyle thinBoldFont11;
    @Override
    public int type() {
        return ContentStyle.defaultStyle;
    }

    @Override
    public void setWorkBook(Workbook workBook) {
        thinBoldFont11 = CellStyleFactory.getHxCellStyle(CellStyleType.thinFont11).getCellStyle(workBook);
    }

    @Override
    public CellStyle getCellStyle(int rowIndex, Object obj, PoiColumnInfo poiColumnInfo) {
        return thinBoldFont11;
    }
}
