package org.layz.hx.poi.style.cell;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.base.inte.poi.CellStyleType;

public class ThinFont11 implements HxCellStyle {
    @Override
    public int type() {
        return CellStyleType.thinFont11;
    }

    @Override
    public CellStyle getCellStyle(Workbook workbook) {
        Font bf11=workbook.createFont();
        bf11.setFontHeightInPoints((short) 11);

        CellStyle ThinFont11=workbook.createCellStyle();
        ThinFont11.setBorderTop(BorderStyle.THIN);
        ThinFont11.setBorderBottom(BorderStyle.THIN);
        ThinFont11.setBorderLeft(BorderStyle.THIN);
        ThinFont11.setBorderRight(BorderStyle.THIN);
        ThinFont11.setWrapText(true);
        ThinFont11.setFont(bf11);
        return ThinFont11;
    }
}
