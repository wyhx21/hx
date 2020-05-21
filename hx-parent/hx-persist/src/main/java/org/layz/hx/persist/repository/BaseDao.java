package org.layz.hx.persist.repository;

import org.layz.hx.base.pojo.Page;
import org.layz.hx.base.pojo.Pageable;

import java.util.List;

public interface BaseDao<T> {
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
	 *
	 * @param id
	 * @return
	 */
	Integer deleteById(Long id);

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
	 * @param t
	 * @return
	 */
    Long findCountByEntity(T t);

    /**
	 * @return
	 */
    List<T> findAll();
}
