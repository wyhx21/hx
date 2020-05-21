package org.layz.hx.core.util.cacheseter;

import org.layz.hx.core.inte.FormatCacheSeter;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.util.factory.DataReaderFactory;
import org.layz.hx.core.util.reader.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractCacheSeter  implements FormatCacheSeter  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCacheSeter.class);

    @Override
    public final void setCache(Map<Object, Object> cacheMap, List<FieldColumnInfo> collect, List<?> list) {
        List<Long> idList = new ArrayList<>();
        for (Object obj : list) {
            DataReader dataReader = DataReaderFactory.getDataReader(obj);
            for (FieldColumnInfo columnInfo : collect) {
                try {
                    Object invoke = dataReader.getObject(obj, columnInfo);
                    if(Long.class.isInstance(invoke) && !idList.contains(invoke)) {
                        idList.add((Long) invoke);
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        }
        if(!idList.isEmpty()) {
            setCache(idList,cacheMap);
        }
    }

    /**
     * @param idList
     * @param cacheMap
     */
    protected abstract void setCache(List<Long> idList, Map<Object, Object> cacheMap);
}
