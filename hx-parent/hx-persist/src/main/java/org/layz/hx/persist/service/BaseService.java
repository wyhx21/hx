package org.layz.hx.persist.service;

import org.layz.hx.base.pojo.Page;
import org.layz.hx.base.pojo.Pageable;

import java.util.List;

public interface BaseService<T>{
    /**
     * @param t
     * @return
     */
    Integer persistEntity(T t);
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
     * _更新非空字段
     * @param t
     * @return
     */
    Integer updateNotnull(T t);
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
     * _分页查询
     * @param t
     * @param pageable
     * @return
     */
    Page<T> findPage(T t, Pageable pageable);

    /**
     * @return
     */
    List<T> findAll();
}
