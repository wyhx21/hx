package org.layz.hx.poi.style.title;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.base.inte.poi.CellStyleType;
import org.layz.hx.base.inte.poi.TitleStyle;
import org.layz.hx.core.pojo.info.PoiColumnInfo;
import org.layz.hx.poi.factory.CellStyleFactory;

public class DefaultTitleStyle implements TitleCellStyle {
    private CellStyle centerBoldFont15;
    private CellStyle thinBackGroundBoldFont11;
    @Override
    public int type() {
        return TitleStyle.defaultStyle;
    }

    @Override
    public void setWookBook(Workbook wookBook) {
        centerBoldFont15 = CellStyleFactory.getHxCellStyle(CellStyleType.centerBoldFont15).getCellStyle(wookBook);
        thinBackGroundBoldFont11 = CellStyleFactory.getHxCellStyle(CellStyleType.thinBackGroundBoldFont11).getCellStyle(wookBook);
    }

    @Override
    public CellStyle getTitleStyle() {
        return centerBoldFont15;
    }

    @Override
    public CellStyle getHeadleStyle(PoiColumnInfo poiColumnInfo) {
        return thinBackGroundBoldFont11;
    }
}
