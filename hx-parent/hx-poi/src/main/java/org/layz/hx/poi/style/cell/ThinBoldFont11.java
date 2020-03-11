package org.layz.hx.poi.style.cell;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.base.inte.poi.CellStyleType;

/**实线边框11号粗体*/
public class ThinBoldFont11 implements HxCellStyle {
    @Override
    public int type() {
        return CellStyleType.thinBoldFont11;
    }

    @Override
    public CellStyle getCellStyle(Workbook workbook) {
        Font bf11=workbook.createFont();
        bf11.setFontHeightInPoints((short) 11);

        CellStyle thinBoldFont11=workbook.createCellStyle();
        thinBoldFont11.setBorderTop(BorderStyle.THIN);
        thinBoldFont11.setBorderBottom(BorderStyle.THIN);
        thinBoldFont11.setBorderLeft(BorderStyle.THIN);
        thinBoldFont11.setBorderRight(BorderStyle.THIN);
        thinBoldFont11.setWrapText(true);
        thinBoldFont11.setFont(bf11);
        return thinBoldFont11;
    }
}
