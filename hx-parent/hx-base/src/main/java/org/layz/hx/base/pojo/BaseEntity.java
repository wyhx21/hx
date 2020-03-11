package org.layz.hx.base.pojo;

import java.io.Serializable;

public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2119695793661307225L;

	private Integer success;
	
	public Integer getSuccess() {
		return success;
	}
	
	public void setSuccess(Integer success) {
		this.success = success;
	}
}
