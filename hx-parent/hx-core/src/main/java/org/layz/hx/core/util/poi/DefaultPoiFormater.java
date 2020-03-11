package org.layz.hx.core.util.poi;

import org.layz.hx.base.inte.poi.FormaterType;
import org.layz.hx.core.inte.PoiFormater;
import org.layz.hx.core.pojo.info.PoiColumnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPoiFormater implements PoiFormater {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPoiFormater.class);
    @Override
    public int type() {
        return FormaterType.defaultType;
    }

    @Override
    public Object format(Object rowData, PoiColumnInfo poiColumnInfo) {
        try {
            Object value = poiColumnInfo.getMethodGet().invoke(rowData);
            if(Number.class.isInstance(value)) {
                value = poiColumnInfo.getPoiColumn().param() + value;
            }
            return value;
        } catch (Exception e) {
            LOGGER.error("",e);
            return null;
        }
    }
}
