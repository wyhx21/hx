package org.layz.hx.persist.complete;

import org.layz.hx.base.entity.BaseEntity;
import org.layz.hx.base.inte.entity.Complete;

import java.util.Date;

public class BaseEntityComplete implements Complete {
    @Override
    public boolean support(Object obj) {
        return BaseEntity.class.isInstance(obj);
    }

    @Override
    public void complete(Object obj) {
        BaseEntity baseEntity = (BaseEntity) obj;
        Date createdDate = baseEntity.getCreatedDate();
        if(null == createdDate) {
            createdDate = new Date();
        }
        baseEntity.setCreatedDate(createdDate);
        if(null == baseEntity.getLastModifiedDate()) {
            baseEntity.setLastModifiedDate(createdDate);
        }
        if(null == baseEntity.getLastModifiedBy()) {
            baseEntity.setLastModifiedBy(baseEntity.getCreatedBy());
        }
    }
}
