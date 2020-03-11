package org.layz.hx.poi.factory;

import org.layz.hx.base.inte.poi.TitleStyle;
import org.layz.hx.poi.style.title.TitleCellStyle;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class TitleCellStyleFactory {
    private static final Map<Integer, TitleCellStyle> store;

    static {
        store = new ConcurrentHashMap<>();
        for (TitleCellStyle titleCellStyle : ServiceLoader.load(TitleCellStyle.class)) {
            store.put(titleCellStyle.type(),titleCellStyle);
        }
    }

    /**
     * @param type
     * @return
     */
    public static TitleCellStyle getTitleCellStyle(Integer type){
        TitleCellStyle titleCellStyle = store.get(type);
        if(null != titleCellStyle) {
            return titleCellStyle;
        }
        return store.get(TitleStyle.defaultStyle);
    }
}
