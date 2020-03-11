package org.layz.hx.poi.factory;

import org.layz.hx.base.inte.poi.ContentStyle;
import org.layz.hx.poi.style.content.ContentCellStyle;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class ContentCellStyleFactory {
    private static final Map<Integer, ContentCellStyle> store;

    static {
        store = new ConcurrentHashMap<>();
        for (ContentCellStyle contentCellStyle : ServiceLoader.load(ContentCellStyle.class)) {
            store.put(contentCellStyle.type(),contentCellStyle);
        }
    }

    /**
     * @param type
     * @return
     */
    public static ContentCellStyle getContentCellStyle(Integer type){
        ContentCellStyle contentCellStyle = store.get(type);
        if(null != contentCellStyle) {
            return contentCellStyle;
        }
        return store.get(ContentStyle.defaultStyle);
    }
}
