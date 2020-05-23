package org.layz.hx.base.entity;
import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxSupperClass;

import java.io.Serializable;
import java.util.Date;

@HxSupperClass
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8053568662236406287L;
	@HxColumn(sort = 200,definition = "BIGINT(19) COMMENT '创建人'")
	private Long createdBy;
	@HxColumn(sort = 300,definition = "BIGINT(19) COMMENT '修改人'")
	private Long lastModifiedBy;
	@HxColumn(sort = 400,definition = "datetime COMMENT '创建时间'")
	private Date createdDate;
	@HxColumn(sort = 500,definition = "datetime COMMENT '修改时间'")
	private Date lastModifiedDate;
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
