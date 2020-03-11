package org.layz.hx.poi.style.cell;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.layz.hx.base.inte.poi.CellStyleType;

/**无边框15号粗体(居中)*/
public class CenterBoldFont15 implements HxCellStyle {
    @Override
    public int type() {
        return CellStyleType.centerBoldFont15;
    }

    @Override
    public CellStyle getCellStyle(Workbook workbook) {
        Font bf15=workbook.createFont();
        bf15.setFontHeightInPoints((short) 15);
        bf15.setBold(true);

        CellStyle centerBoldFont15=workbook.createCellStyle();
        centerBoldFont15.setFont(bf15);
        centerBoldFont15.setAlignment(HorizontalAlignment.CENTER);
        return centerBoldFont15;
    }
}
