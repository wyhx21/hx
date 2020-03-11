package org.layz.hx.core.inte;

import org.layz.hx.core.pojo.info.PoiColumnInfo;

public interface PoiFormater {
    /**
     * @return
     */
    int type();

    /**
     * 数据格式化
     * @param rowData
     * @param poiColumnInfo
     * @return
     */
    Object format(Object rowData, PoiColumnInfo poiColumnInfo);
}
