package org.layz.hx.poi.style.cell;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public interface HxCellStyle {
    /**
     * 类型
     * @return
     */
    int type();

    /**
     * 获取样式
     * @param workbook
     * @return
     */
    CellStyle getCellStyle(Workbook workbook);
}
