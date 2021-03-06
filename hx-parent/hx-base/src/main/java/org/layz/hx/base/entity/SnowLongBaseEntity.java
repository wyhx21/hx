package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.inte.entity.LongIdEntity;

public class SnowLongBaseEntity extends DeletedEntity implements LongIdEntity {
    @HxColumn(sort = -1,definition = "BIGINT(19) COMMENT '主键ID'")
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
