package org.layz.hx.base.pojo;

import java.io.Serializable;

public class Pageable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7097542899824798280L;
	
	/**
	 * _从1开始
	 */
	private Integer page;
	
	private Integer size;
	
	private String orderBy;
	
	public Pageable() {
	}
	
	public Pageable(String orderBy) {
		this(null, null, orderBy);
	}
	
	public Pageable(Integer page, Integer size) {
		this(page, size, null);
	}
	
	public Pageable(Integer page, Integer size, String orderBy) {
		this.page = page;
		this.size = size;
		this.orderBy = orderBy;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public Long getOffset() {
		if(null == page || null == size) {
			return null;
		}
		Integer offset = (page - 1) * size;
		return offset.longValue();
	}
}
