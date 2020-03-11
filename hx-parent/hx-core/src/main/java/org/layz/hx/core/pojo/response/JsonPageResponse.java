package org.layz.hx.core.pojo.response;

import java.util.List;

public class JsonPageResponse extends JsonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344459013999731472L;

	private Long total;
	
	private Integer page;
	
	private Integer size;
	
	private List<?> data;
	
	private Long totalPage;

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

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
	
}
