package org.layz.hx.persist.complete;

import org.layz.hx.base.entity.DeletedEntity;
import org.layz.hx.base.inte.entity.Complete;
import org.layz.hx.base.type.DeletedEnum;

public class DeletedEntityComplete implements Complete {
    @Override
    public boolean support(Object obj) {
        return DeletedEntity.class.isInstance(obj);
    }

    @Override
    public void complete(Object obj) {
        DeletedEntity entity = (DeletedEntity) obj;
        Integer deleted = entity.getDeleted();
        if(null == deleted) {
            entity.setDeleted(DeletedEnum.DISABLE.getValue());
        }
    }
}
