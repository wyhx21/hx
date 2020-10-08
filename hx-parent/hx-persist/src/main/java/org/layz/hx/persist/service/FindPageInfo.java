package org.layz.hx.persist.service;

import org.layz.hx.base.pojo.Page;
import org.layz.hx.base.pojo.Pageable;

public interface FindPageInfo<T> {
    /**
     * _分页查询
     * @param t
     * @param pageable
     * @return
     */
    Page<T> findPage(T t, Pageable pageable);
}
