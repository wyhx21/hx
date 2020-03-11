package org.layz.hx.base.pojo;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3043586090742172428L;

	private Long total;
	
	private Integer page;
	
	private Integer size;
	
	private List<T> data;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public Long getToalPage() {
		if(null == total || null == page || null == size) {
			return null;
		}
		Long pageSize = total / size;
		if(total % size > 0) {
			pageSize++;
		}
		return pageSize;
	}
}
