package org.layz.hx.persist.service;

import org.layz.hx.core.inte.FindPageWrapper;

import java.util.List;

public interface BaseService<T> extends FindPageWrapper<T> {
    /**
     * @param t
     * @return
     */
    Integer persistEntity(T t);
    /**
     * @param list
     */
    void persistBatch(List<T> list);
    /**
     *
     * @param t
     * @return
     */
    Integer deleteByEntity(T t);
    /**
     * _更新所有字段
     * @param t
     * @return
     */
    Integer update(T t);

    /**
     * @param list
     */
    void updateBatch(List<T> list);

    /**
     *
     * @param id
     * @return
     */
    Integer deleteById(Long id);
    /**
     * _根据id查询
     * @param id
     * @return
     */
    T findById(Long id);
    /**
     * _根据ids查询
     * @param ids
     * @return
     */
    List<T> findByIds(List<Long> ids);
    /**
     * _根据条件查查
     * @param t
     * @return
     */
    List<T> findByEntity(T t);

    /**
     * _根据条件查查
     * @param t
     * @return
     */
    List<T> findByEntity(T t, String orderBy);

    /**
     * @param t
     * @return
     */
    Long findCountByEntity(T t);
    /**
     * @return
     */
    List<T> findAll();
}
