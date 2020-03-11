package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxSupperClass;

@HxSupperClass
public class DeletedEntity extends BaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = 8053568662236406287L;
    @HxColumn(sort = 101)
    private Integer deleted;

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
