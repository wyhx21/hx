package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxSupperClass;

@HxSupperClass
public class AutoLongBaseEntity extends DeletedEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2641209235577591499L;
	@HxColumn
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
