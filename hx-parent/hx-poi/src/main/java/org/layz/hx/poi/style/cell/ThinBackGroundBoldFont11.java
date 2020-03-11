package org.layz.hx.poi.style.cell;

import org.apache.poi.ss.usermodel.*;
import org.layz.hx.base.inte.poi.CellStyleType;

/**实线边框11号粗体(带背景色)*/
public class ThinBackGroundBoldFont11 implements HxCellStyle {
    @Override
    public int type() {
        return CellStyleType.thinBackGroundBoldFont11;
    }

    @Override
    public CellStyle getCellStyle(Workbook workbook) {
        Font bf11=workbook.createFont();
        bf11.setFontHeightInPoints((short) 11);
        bf11.setBold(true);

        CellStyle thinBackGroundBoldFont11 = workbook.createCellStyle();
        thinBackGroundBoldFont11.setBorderTop(BorderStyle.THIN);
        thinBackGroundBoldFont11.setBorderBottom(BorderStyle.THIN);
        thinBackGroundBoldFont11.setBorderLeft(BorderStyle.THIN);
        thinBackGroundBoldFont11.setBorderRight(BorderStyle.THIN);
        thinBackGroundBoldFont11.setAlignment(HorizontalAlignment.CENTER);
        thinBackGroundBoldFont11.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        thinBackGroundBoldFont11.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        thinBackGroundBoldFont11.setWrapText(true);
        thinBackGroundBoldFont11.setFont(bf11);
        return thinBackGroundBoldFont11;
    }
}
