package org.layz.hx.poi.style.cell;

import org.apache.poi.ss.usermodel.*;
import org.layz.hx.base.inte.poi.CellStyleType;

public class ThinBackBoldRedFont11 implements HxCellStyle {
    @Override
    public int type() {
        return CellStyleType.thinBackBoldRedFont11;
    }

    @Override
    public CellStyle getCellStyle(Workbook workbook) {
        Font bf11=workbook.createFont();
        bf11.setFontHeightInPoints((short) 11);
        bf11.setColor(Font.COLOR_RED);
        bf11.setBold(true);

        CellStyle thinBackBoldRedFont11 = workbook.createCellStyle();
        thinBackBoldRedFont11.setBorderTop(BorderStyle.THIN);
        thinBackBoldRedFont11.setBorderBottom(BorderStyle.THIN);
        thinBackBoldRedFont11.setBorderLeft(BorderStyle.THIN);
        thinBackBoldRedFont11.setBorderRight(BorderStyle.THIN);
        thinBackBoldRedFont11.setFillPattern(FillPatternType.THIN_BACKWARD_DIAG);
        thinBackBoldRedFont11.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());
        thinBackBoldRedFont11.setWrapText(true);
        thinBackBoldRedFont11.setFont(bf11);
        return thinBackBoldRedFont11;
    }
}
