package org.layz.hx.spring.cacheseter;

import org.layz.hx.base.inte.QueryByIdListWrapper;
import org.layz.hx.base.inte.entity.LongIdEntity;
import org.layz.hx.core.util.cacheseter.AbstractCacheSeter;
import org.layz.hx.spring.util.SpringContextUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class SpringCacheSeter extends AbstractCacheSeter {
    private QueryByIdListWrapper queryByIdListWrapper;
    /**
     * @param idList
     * @param cacheMap
     */
    protected void setCache(List<Long> idList, Map<Object, Object> cacheMap) {
        if(null == queryByIdListWrapper) {
            queryByIdListWrapper = SpringContextUtil.getBean(getWrapperClass());
        }
        List<? extends LongIdEntity> customerList = queryByIdListWrapper.queryByIdList(idList);
        if(null != customerList && !customerList.isEmpty()) {
            Map<Long,? extends LongIdEntity> cache = customerList.stream().collect(Collectors.toMap(LongIdEntity::getId, Function.identity(),(key1, key2) -> key1, HashMap::new));
            cacheMap.put(formatType(),cache);
        }
    }

    protected Class<? extends QueryByIdListWrapper> getWrapperClass() {
        return null;
    }
}
