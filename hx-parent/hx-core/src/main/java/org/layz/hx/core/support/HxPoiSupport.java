package org.layz.hx.core.support;

import org.layz.hx.base.annotation.HxPoiColumn;
import org.layz.hx.base.annotation.HxPoiFile;
import org.layz.hx.core.pojo.info.PoiColumnInfo;
import org.layz.hx.core.pojo.info.PoiFileInfo;
import org.layz.hx.core.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HxPoiSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(HxPoiSupport.class);
    private static final Map<Object, PoiFileInfo> store = new ConcurrentHashMap<>();

    public static PoiFileInfo getPoiFileInfo(Class clazz){
        PoiFileInfo poiFileInfo = store.get(clazz);
        if(null != poiFileInfo) {
            return poiFileInfo;
        }
        LOGGER.debug("get new poiFileInfo, class: {}", clazz);
        poiFileInfo = getnNewPoiFileInfo(clazz);
        if(null != poiFileInfo) {
            store.put(clazz,poiFileInfo);
        }
        return poiFileInfo;
    }

    private static PoiFileInfo getnNewPoiFileInfo(Class clazz) {
        if(!clazz.isAnnotationPresent(HxPoiFile.class)) {
            LOGGER.debug("please set annotation @HxPoiFile");
            return null;
        }
        HxPoiFile hxPoiFile = (HxPoiFile) clazz.getAnnotation(HxPoiFile.class);
        String simpleName = clazz.getSimpleName();
        String title = hxPoiFile.value();
        if(title.length() < 1) {
            title = simpleName;
        }
        String sheetName = hxPoiFile.sheetName();
        if(sheetName.length() < 1) {
            sheetName = simpleName;
        }
        List<PoiColumnInfo> infos = new ArrayList<>();
        setFiledInfo(infos,clazz);
        setFiledInfo(infos,clazz,hxPoiFile.columns());
        infos = infos.stream().sorted(Comparator.comparing(PoiColumnInfo::getSort)).collect(Collectors.toList());
        int sum = infos.stream().mapToInt(PoiColumnInfo::getCols).sum();
        PoiFileInfo poiFileInfo = new PoiFileInfo();
        poiFileInfo.setPoiFile(hxPoiFile);
        poiFileInfo.setSheetName(sheetName);
        poiFileInfo.setTitleName(title);
        poiFileInfo.setColumnInfos(infos);
        poiFileInfo.setTotalCol(sum);
        return poiFileInfo;
    }

    /**
     * @param infos
     * @param clazz
     * @param columns
     */
    private static void setFiledInfo(List<PoiColumnInfo> infos, Class clazz, HxPoiColumn[] columns) {
        if(null == clazz) {
            return;
        }
        Map<String, HxPoiColumn> map = Arrays.stream(columns)
                .collect(Collectors.toMap(HxPoiColumn::field, Function.identity(), (key1,key2) -> key1, HashMap::new));
        ClassUtil.setFieldInfo(infos,PoiColumnInfo.class,clazz,a -> map.keySet().contains(a.getName()));
        for (PoiColumnInfo info : infos) {
            HxPoiColumn poiColumn = map.get(info.getFieldName());
            setInfo(info,poiColumn);
        }
    }

    /**
     * @param infos
     * @param clazz
     */
    private static void setFiledInfo(List<PoiColumnInfo> infos, Class clazz) {
        if(null == clazz) {
            return;
        }
        ClassUtil.setFieldInfo(infos,PoiColumnInfo.class,clazz,a -> a.isAnnotationPresent(HxPoiColumn.class));
        for (PoiColumnInfo info : infos) {
            HxPoiColumn poiColumn = info.getField().getAnnotation(HxPoiColumn.class);
            setInfo(info,poiColumn);
        }
    }

    /**
     * @param info
     * @param poiColumn
     */
    private static void setInfo(PoiColumnInfo info, HxPoiColumn poiColumn){
        if(null == poiColumn) {
            return;
        }
        String field = poiColumn.field();
        if(field.length() < 1) {
            field = info.getFieldName();
        }
        String title = poiColumn.title();
        if(title.length() < 1) {
            title = info.getFieldName();
        }
        info.setColumnName(field);
        info.setTitle(title);
        info.setPoiColumn(poiColumn);
        info.setCols(poiColumn.cols());
        info.setSort(poiColumn.value());
        info.setWidth(poiColumn.width());
    }
}
