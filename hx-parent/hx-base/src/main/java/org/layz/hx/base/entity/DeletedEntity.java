package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxSupperClass;

@HxSupperClass
public class DeletedEntity extends BaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = 8053568662236406287L;
    @HxColumn(sort = 100, definition = "TINYINT(2) COMMENT 'DeletedEnum 是否有效0无效,1有效'")
    private Integer deleted;

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
