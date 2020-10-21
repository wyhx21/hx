package org.layz.hx.core.inte;

import org.layz.hx.base.pojo.Page;
import org.layz.hx.base.pojo.Pageable;

public interface FindPageWrapper<T> {
    /**
     * _分页查询
     * @param t
     * @param pageable
     * @return
     */
    Page<T> findPage(T t, Pageable pageable);
}
