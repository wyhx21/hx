package org.layz.hx.core.util.factory;

import org.layz.hx.base.inte.poi.FormaterType;
import org.layz.hx.core.inte.PoiFormater;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class PoiFormaterFactory {

    public static final Map<Integer,PoiFormater> formaterMap;

    static {
        formaterMap = new ConcurrentHashMap<>();
        for (PoiFormater formater : ServiceLoader.load(PoiFormater.class)) {
            formaterMap.put(formater.type(),formater);
        }
    }

    /**
     * @param type
     * @return
     */
    public static PoiFormater getFormater(Integer type){
        PoiFormater poiFormater = formaterMap.get(type);
        if(null != poiFormater) {
            return poiFormater;
        }
        return formaterMap.get(FormaterType.defaultType);
    }
}
