package org.layz.hx.persist.complete;

import org.layz.hx.base.entity.SnowLongBaseEntity;
import org.layz.hx.base.inte.entity.Complete;
import org.layz.hx.core.util.SnowFlakeUtil;

public class SnowLongEntityComplete implements Complete {
    @Override
    public boolean support(Object obj) {
        return SnowLongBaseEntity.class.isInstance(obj);
    }

    @Override
    public void complete(Object obj) {
        SnowLongBaseEntity entity = (SnowLongBaseEntity) obj;
		if(null == entity.getId()) {
			entity.setId(SnowFlakeUtil.getSnowFlake().nextId());
		}
    }
}
