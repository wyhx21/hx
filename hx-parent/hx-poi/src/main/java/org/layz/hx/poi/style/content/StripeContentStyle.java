package org.layz.hx.poi.style.content;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.base.inte.poi.CellStyleType;
import org.layz.hx.base.inte.poi.ContentStyle;
import org.layz.hx.core.pojo.info.PoiColumnInfo;
import org.layz.hx.poi.factory.CellStyleFactory;

public class StripeContentStyle implements ContentCellStyle {
    private CellStyle oddsStyle;
    private CellStyle evenStyle;

    @Override
    public int type() {
        return ContentStyle.stripeStyle;
    }

    @Override
    public void setWorkBook(Workbook workBook) {
        oddsStyle = CellStyleFactory.getHxCellStyle(CellStyleType.thinFont11).getCellStyle(workBook);
        evenStyle = CellStyleFactory.getHxCellStyle(CellStyleType.thinBackFont11).getCellStyle(workBook);
    }

    @Override
    public CellStyle getCellStyle(int rowIndex, Object obj, PoiColumnInfo poiColumnInfo) {
        return (rowIndex % 2 == 1) ? oddsStyle : evenStyle;
    }
}
