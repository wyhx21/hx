package org.layz.hx.poi.style.cell;

import org.apache.poi.ss.usermodel.*;
import org.layz.hx.base.inte.poi.CellStyleType;

public class ThinBackFont11 implements HxCellStyle{
    @Override
    public int type() {
        return CellStyleType.thinBackFont11;
    }

    @Override
    public CellStyle getCellStyle(Workbook workbook) {
        Font bf11=workbook.createFont();
        bf11.setFontHeightInPoints((short) 11);

        CellStyle thinBackFont11=workbook.createCellStyle();
        thinBackFont11.setBorderTop(BorderStyle.THIN);
        thinBackFont11.setBorderBottom(BorderStyle.THIN);
        thinBackFont11.setBorderLeft(BorderStyle.THIN);
        thinBackFont11.setBorderRight(BorderStyle.THIN);
        thinBackFont11.setFillPattern(FillPatternType.THIN_VERT_BANDS);
        thinBackFont11.setFillForegroundColor(IndexedColors.YELLOW.index);
        thinBackFont11.setWrapText(true);
        thinBackFont11.setFont(bf11);
        return thinBackFont11;
    }
}
