package org.layz.hx.poi.factory;

import org.layz.hx.base.inte.poi.CellStyleType;
import org.layz.hx.poi.style.cell.HxCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class CellStyleFactory {
    private static final Map<Integer, HxCellStyle> store;
    private static final Logger LOGGER = LoggerFactory.getLogger(CellStyleFactory.class);
    static {
        store = new ConcurrentHashMap<>();
        for (HxCellStyle hxCellStyle : ServiceLoader.load(HxCellStyle.class)) {
            store.put(hxCellStyle.type(),hxCellStyle);
        }
    }

    /**
     * @param type
     * @return
     */
    public static HxCellStyle getHxCellStyle(Integer type){
        HxCellStyle hxCellStyle = store.get(type);
        if(null != hxCellStyle) {
            return hxCellStyle;
        }
        LOGGER.debug("hxCellStyle is null, typt: {}", type);
        return store.get(CellStyleType.thinBoldFont11);
    }
}
