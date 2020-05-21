package org.layz.hx.base.inte;

import org.layz.hx.base.inte.entity.LongIdEntity;

import java.util.List;

public interface QueryByIdListWrapper {
    /**
     * 根据id列表查询
     * @param idList
     * @return
     */
    List<? extends LongIdEntity> queryByIdList(List<Long> idList);
}
