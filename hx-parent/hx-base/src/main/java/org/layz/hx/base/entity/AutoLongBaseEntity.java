package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxSupperClass;
import org.layz.hx.base.inte.entity.AutoKeyEntity;
import org.layz.hx.base.inte.entity.LongIdEntity;

@HxSupperClass
public class AutoLongBaseEntity extends DeletedEntity implements LongIdEntity, AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2641209235577591499L;
	@HxColumn
	private Long id;
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
